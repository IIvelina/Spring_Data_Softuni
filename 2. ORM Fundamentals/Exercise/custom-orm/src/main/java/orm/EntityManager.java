package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private static final String UPDATE_WITH_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s";
    private static final String SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE = "SELECT %s FROM %s %s";
    private Connection connection;

    //конструктор
    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field idColumn = getIdColumn(entity.getClass());

        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);


        if (idValue == null || (long) idValue == 0){
            return toInsert(entity, idColumn);
        }

        return doUpdate(entity, idColumn, idValue);

    }

    private boolean toInsert(E entity, Field idColumn) throws SQLException, IllegalAccessException {
        //как намираме името на таблицата?
        //Трбва да вземем анотацията, която се намира на класа

        String tableName = getTableName(entity.getClass());
        //%s -> table name
        //(%s) -> имената на всички колони, които не са ид колоната

        String tableFields = getColumnWithoutId(entity.getClass());

        String tableValues = getColumnsValuesWithoutId(entity);

        String insertQuery = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                tableFields,
                tableValues);

        return connection.prepareStatement(insertQuery).execute();
    }

    private String getColumnsValuesWithoutId(E entity) throws IllegalAccessException {
        Class<?> aClass = entity.getClass();
        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(entity);
            if (o instanceof String || o instanceof LocalDate){
                values.add("'" + o.toString() + "'");
            }else {
                values.add(o.toString());
            }

        }
        return String.join(",", values);
    }

    private String getColumnWithoutId(Class<?> aClass) {
        //филтрираме всичките полета, където нямаме ид колона
        //след филтъра са останали само column нещата и искаме всяко едно от тях
        //да го мапнем към всеки един фийлд
        //това ще ни даде вскички анотации на колоните
        //и това искаме да го мапнем и тъй като е масив искаме нулевата искаме нейма

        //.filter(f -> f.isAnnotationPresent(Column.class))
        //за да се подсигурим че няма да гръмне, ако е анаторано с колумн
        //взиаме колумн нещата
       return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> f.getAnnotationsByType(Column.class))
                .map(a -> a[0].name())
                .collect(Collectors.joining(","));

    }

    private String getTableName(Class<?> aClass) {
        //как намираме името на таблицата?
        //Трбва да вземем анотацията, която се намира на класа

        //това връща празен масив, ако такава анотация не съществува
        Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0){
            throw new UnsupportedOperationException("Class must be Entity");
        }
        return annotationsByType[0].name();
    }


    private boolean doUpdate(E entity, Field idColumn, Object idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());
        List<String> columns = Collections.singletonList(getColumnWithoutId(entity.getClass()));
        List<String> values = Collections.singletonList(getColumnsValuesWithoutId(entity));

        List<String> columnsWithValues = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            String s = columns.get(i) + "=" + values.get(i);

            columnsWithValues.add(s);
        }

        String idCondition = String.format("%s=%s", idColumn.getName(), idValue.toString());

        String updateQuery = String.format(UPDATE_WITH_WHERE_TEMPLATE,
                tableName,
                String.join(",", columnsWithValues),
                idCondition);

        PreparedStatement statement = connection.prepareStatement(updateQuery);
        int updateCount = statement.executeUpdate();

        return updateCount == 1;
    }

    @Override
    public Iterable find(Class table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        String fieldList = "*";
        String tableName = getTableName(table);
        String whereClause = where == null ? "" : "WHWRE " + where;

        String selectStatement = String.format(SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE,
                fieldList,
                tableName,
                whereClause);

        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<E> result = new ArrayList<>();
        while (resultSet.next()){
            E current = generateEntity(table, resultSet);
            result.add(current);
        }
        return result;
    }

    private E generateEntity(Class<E> table, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        //Create object
        E result = table.getDeclaredConstructor().newInstance();

        //Fill object with data
        Field[] declaredFields = table.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            fillField(result, declaredField, resultSet);
        }
        return result;
    }

    private void fillField(E result, Field declaredField, ResultSet resultSet) throws SQLException, IllegalAccessException {

        String dbFieldName = declaredField.getAnnotation(Column.class).name();

        //dbFieldType
        Class<?> javaType = declaredField.getType();
        declaredField.setAccessible(true);

        if (javaType == int.class || javaType == Integer.class){
            int value = resultSet.getInt(dbFieldName);
            declaredField.setInt(result, value);
        }else   if (javaType == long.class || javaType == Long.class){
            long value = resultSet.getLong(dbFieldName);
            declaredField.setLong(result, value);
        } if (javaType == LocalDate.class){
            LocalDate value = resultSet.getObject(dbFieldName, LocalDate.class);
            declaredField.set(result, value);
        } if (javaType == String.class){
            String value = resultSet.getString(dbFieldName);
            declaredField.set(result, value);
        }else {
            throw new RuntimeException("Unsupported type " + javaType);
        }
    }

    @Override
    public Object findFirst(Class table) {


        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String tableName = getTableName(table);

        String query = String.format("SELECT * FROM %s %s LIMIT 1;",
                tableName, where != null ? " WHERE " + where : "");

        ResultSet resultSet = statement.executeQuery(query);
        E entity = table.getDeclaredConstructor().newInstance();
        resultSet.next();
        fillEntity(table, resultSet, entity);
        return entity;


    }



    private void fillEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] declaredFields = Arrays.stream(table.getDeclaredFields())
                .toArray(Field[]::new);

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            fillField(entity, declaredField, resultSet);
        }
    }

    //за да стигнем до тази id колона, трябва да обиколим
    //всички полена на нашия клас, да видим какви анотации
    //имат те и да намерем точно този с анитация id;

    //clazz.getDeclaredField() -> това е масив с всички полета, които ние имаме в рамките на нашия клас
    
    private Field getIdColumn(Class<?> clazz){
    Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            boolean annotationPresent = declaredField.isAnnotationPresent(Id.class);

            if (annotationPresent){
                return declaredField;
            }
        }
        throw new RuntimeException("Entity has no Id column");
    }


    @Override
    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        String query = String.format("CREATE TABLE %s ( id INT AUTO_INCREMENT PRIMARY KEY, %s);",
                tableName, getAllFieldsAndDataTypes(entityClass));

        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
    }

    private Object getAllFieldsAndDataTypes(Class<E> entityClass) {
       return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(field -> {
            String fieldName = field.getAnnotationsByType(Column.class)[0].name();
            Class<?> type = field.getType();
            String sqlType = "";
            if (type ==  Integer.class || type == int.class){
                sqlType = "INT";
            }else if (type == String.class){
                sqlType = "VARCHAR(200)";
            }else if (type == LocalDate.class){
                sqlType = "DATE";
            }
            return fieldName + " " + sqlType;
        })
                .collect(Collectors.joining(","));
    }

    @Override
    public void doAlter(E entity) throws SQLException {
    String tableName = getTableName(entity.getClass());
    String query = String.format("ALTER TABLE %s ADD COLUMN %s;",
            tableName,
            getNewFields(entity.getClass()));

    connection.prepareStatement(query).executeUpdate();
    }

    private Object getNewFields(Class<?> aClass) throws SQLException {

        StringBuilder result = new StringBuilder();
        Set<String> fields = getAllFieldsFromTable();

        Arrays.stream(aClass
                .getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    String fieldName = field.getName();
                    if (!fields.contains(fieldName)){
                        result.append(getNameAndDateTypeOfField(field));
                    }
                });
        return result.toString();
    }


    private Set<String> getAllFieldsFromTable() throws SQLException {
        Set<String> allFields = new HashSet<>();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT `COLUMN_NAME` FROM " +
                        "`INFORMATION_SCHEMA`.`COLUMNS` " +
                        " WHERE `TABLE_SCHEMA`='test' AND  `COLUMN_NAME` != 'id' " +
                        "AND `TABLE_NAME` = 'users';");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            allFields.add(resultSet.getString(1));
        }
        return allFields;
    }

}
