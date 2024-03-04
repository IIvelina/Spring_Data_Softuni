import entities.BasicLabel;
import entities.BasicShampoo;
import entities.ProductionBatch;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        BasicLabel label = new BasicLabel("blue");
        ProductionBatch batch = new ProductionBatch(LocalDate.now());

        entityManager.persist(batch);
        // Запазване на обекта ProductionBatch в базата данни

        BasicShampoo shampoo = new BasicShampoo("shower", label, batch);
        // Подаване на batch като аргумент

        entityManager.persist(label);

        entityManager.persist(shampoo);
        // Запазване на обекта BasicShampoo в базата данни

        entityManager.getTransaction().commit();
    }
}