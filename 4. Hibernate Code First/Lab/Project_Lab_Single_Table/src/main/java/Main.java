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



       // Bike bike = new Bike();
        Bike bike1 = new Bike("Колело1", "Байк", new BigDecimal("15000"), "Бензин");
       // Car car = new Car();
        Car car1 = new Car("Седан", "Волво", new BigDecimal("25000"), "Бензин", 5);
      //  Plane plane = new Plane();
        Plane plane1 = new Plane("Боинг", "747", new BigDecimal("100000000"), "Керосин", 416);
      //  Truck truck = new Truck();
        Truck truck1 = new Truck("Камион", "Мерцедес", new BigDecimal("50000"), "Дизел", 10.5);


      //  entityManager.persist(bike);
      //  entityManager.persist(car);
      //  entityManager.persist(plane);
      //  entityManager.persist(truck);

        entityManager.persist(bike1);
        entityManager.persist(car1);
        entityManager.persist(plane1);
        entityManager.persist(truck1);


        entityManager.getTransaction().commit();
    }
}
