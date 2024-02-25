import entities.User;
import orm.EntityManager;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static orm.MyConnector.createConnection;
import static orm.MyConnector.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {

        createConnection("root", "iV951222&", "custom_orm");
        Connection connection = getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);

       // User user = new User("pesho", 25, LocalDate.now());

       // userEntityManager.persist(user);
       // userEntityManager.persist(user1);
        User user1 = new User("gosho", 23, LocalDate.now());
       // userEntityManager.doCreate(User.class);
        userEntityManager.persist(user1);

        userEntityManager.doAlter(user1);
    }


}

