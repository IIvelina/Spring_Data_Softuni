import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main2 {
    public static void main(String[] args) {

        Configuration cfn = new Configuration();
        cfn.configure();

        SessionFactory sessionFactory =
                cfn.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Student example = new Student();
        session.save(example);

        session.getTransaction().commit();
        session.close();
    }
}
