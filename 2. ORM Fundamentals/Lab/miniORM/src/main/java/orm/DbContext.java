package orm;

import java.sql.SQLException;

public interface DbContext<E> { // Е -> generic
    //ще вмъкне или актуализира обекта в зависимост дали е прикрепен към контекста
    boolean persist(E entity) throws SQLException, IllegalAccessException;
    //връща колекция от всички обекти на обекти от тип E
    Iterable<E> find(Class<E> table);
    //връща колекция от всички обекти на обекти от тип T, отговарящи на критериите, дадени в "where"
    Iterable<E> find(Class<E> table, String where);
    //връща първия обект обект от тип E
    E findFirst(Class<E> table);
    //връща първия обект на обект от тип E, отговарящ на критериите, дадени в "where"
    E findFirst(Class<E> table, String where);
}
