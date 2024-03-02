import entities.Town;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateMain {
    public static void main(String[] args) {

        Configuration cfn = new Configuration();
        cfn.configure();

        SessionFactory sessionFactory = cfn.buildSessionFactory();


        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Town town = session.get(Town.class, 1);

        System.out.println(town);

        session.getTransaction().commit();
    }
}
