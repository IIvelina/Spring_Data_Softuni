import entities.WizardDeposits;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();


        WizardDeposits deposit1 = new WizardDeposits("Harry", "Potter", "The Boy Who Lived", 17, "Ollivander", 11L, "Gold", new Date(), 10000.0, 0.05, 100.0, new Date(), false);
        entityManager.persist(deposit1);

        WizardDeposits deposit2 = new WizardDeposits("Harry", "Potter", "The Boy Who Lived", 17, "Ollivander", 11L, "Gold", new Date(), 10000.0, 0.05, 100.0, new Date(), false);
        entityManager.persist(deposit2);

        WizardDeposits deposit3 = new WizardDeposits("Harry", "Potter", "The Boy Who Lived", 17, "Ollivander", 11L, "Gold", new Date(), 10000.0, 0.05, 100.0, new Date(), false);
        entityManager.persist(deposit3);

        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }
}
