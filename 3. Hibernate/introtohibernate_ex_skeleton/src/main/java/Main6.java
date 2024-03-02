import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main6 {
    public static void main(String[] args) {

        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String addressText = "Vitoshka 15";

        Address address = new Address();
        address.setText(addressText);
        entityManager.persist(address);

        Employee employee = entityManager
                .createQuery("SELECT e FROM Employee e" +
                                " WHERE e.lastName = :name",
                        Employee.class)
                .setParameter("name", input)
                .getSingleResult();


        employee.setAddress(address);

        entityManager.persist(employee);

        entityManager.getTransaction().commit();

    }
}
