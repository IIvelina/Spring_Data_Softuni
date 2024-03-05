import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        // Създаване на обект PlateNumbers
        PlateNumbers plateNumber = new PlateNumbers("ABC123");

        // Запазване на обекта PlateNumbers в базата данни
        entityManager.persist(plateNumber);

        // Създаване на обект Car и присвояване на обекта PlateNumbers към него
        Car car = new Car(5, plateNumber);

        // Запазване на обекта Car в базата данни
        entityManager.persist(car);

        // Комитиране на транзакцията
        entityManager.getTransaction().commit();

        // Затваряне на ентити мениджъра и фабриката
        entityManager.close();
        factory.close();
    }
}
