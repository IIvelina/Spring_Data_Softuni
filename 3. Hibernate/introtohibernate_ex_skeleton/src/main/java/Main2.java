import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {

        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        //искаме да вземем всички градове
        Query from_Town = entityManager.createQuery("SELECT t FROM Town t", Town.class);
        List<Town> resultList = from_Town.getResultList();

        for (Town town : resultList) {
            String name = town.getName();
            if (name.length() <= 5) {
                String upperCase = name.toUpperCase();
                town.setName(upperCase);

                entityManager.persist(town);
            }
        }

        entityManager.getTransaction().commit();
    }
}
