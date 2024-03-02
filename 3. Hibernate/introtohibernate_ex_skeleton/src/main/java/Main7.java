import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main7 {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        //Find all addresses, ordered by the number of employees who live there (descending).
        //Take only the first 10 addresses and print their address text, town name and employee count.

        entityManager
                .createQuery("SELECT a FROM Address a JOIN FETCH a.town " +
                        "ORDER BY SIZE(a.employees) DESC", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(System.out::println);



        entityManager.getTransaction().commit();
    }
}
