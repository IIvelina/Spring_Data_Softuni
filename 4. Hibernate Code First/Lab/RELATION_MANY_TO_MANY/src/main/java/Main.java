import entities.BasicIngredient;
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


        ProductionBatch batch = new ProductionBatch(LocalDate.now());
        BasicLabel label = new BasicLabel("blue");
        BasicShampoo shampoo = new BasicShampoo("shower", label, batch);

        BasicIngredient ingredient1 = new BasicIngredient(100, "B12");
        BasicIngredient ingredient2 = new BasicIngredient(2, "Violet");

        shampoo.addIngredient(ingredient1);
        shampoo.addIngredient(ingredient2);


        entityManager.persist(batch);
        entityManager.persist(label);
        entityManager.persist(shampoo);
        entityManager.persist(ingredient1);
        entityManager.persist(ingredient2);


        entityManager.getTransaction().commit();
    }
}
