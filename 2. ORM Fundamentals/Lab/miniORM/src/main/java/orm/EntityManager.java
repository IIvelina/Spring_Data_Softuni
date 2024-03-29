package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE_WITH_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s";
    private static final String SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE = "SELECT %s FROM %s %s";
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        Field idColumn = getIdColumn(entity);

        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);


        if (idValue == null || (long) idValue == 0){
            return toInsert(entity);
        }

        return doUpdate(entity, idColumn, idValue);
    }

    private boolean doUpdate(E entity, Field idColumn, Object idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        List<String> columns = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

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


    private boolean toInsert(E entity) throws IllegalAccessException, SQLException {
        //Generate INSERT
        // String insertStatement = "INSERT INTO %s (%s) VALUES (%s)";

        //Get table name
        //Collect column without ID
        //Collect values without ID
        String tableName = getTableName(entity);
        List<String> columnsList = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        String formattedInsert = String.format(INSERT_TEMPLATE,
                tableName,
                String.join(",", columnsList),
                String.join(",", values));

        //Execute
        PreparedStatement statement = connection.prepareStatement(formattedInsert);
        int changedRows = statement.executeUpdate();

        //Parse result
        return changedRows == 1;
    }


    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

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
    public E findFirst(Class<E> table) {

        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private String getTableName(E entity) {
       Entity annotation =  entity.getClass().getAnnotation(Entity.class);

       if (annotation == null){
           throw new RuntimeException("No Entity annotation present");
       }

        return annotation.name();
    }

    private String getTableName(Class<E> clazz){
        Entity annotation =  clazz.getAnnotation(Entity.class);

        if (annotation == null){
            throw new RuntimeException("No Entity annotation present");
        }

        return annotation.name();
    }

    private List<String> getColumnsWithoutId(E entity) {
        List<String> result = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if(declaredField.isAnnotationPresent(Id.class)){
                continue;
            }
            if(!declaredField.isAnnotationPresent(Column.class)){
              continue;
            }
            Column column = declaredField.getAnnotation(Column.class);
            if (column == null){
                continue;
            }
            result.add(column.name());
        }
        return null;
    }

    private List<String> getColumnValuesWithoutId(E entity) throws IllegalAccessException {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if(declaredField.isAnnotationPresent(Id.class)){
                continue;
            }
            if(!declaredField.isAnnotationPresent(Column.class)){
                continue;
            }

            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(entity);

            result.add("'" + fieldValue.toString() + "'");
        }

        return result;
    }

    private Field getIdColumn(E entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)){
                return declaredField;
            }
        }
        throw new RuntimeException("Entity has no Id column");
    }
}




