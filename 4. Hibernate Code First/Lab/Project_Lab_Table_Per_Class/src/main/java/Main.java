import entities.Bike;
import entities.Car;
import entities.Plane;
import entities.Truck;

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
        Bike bike1 = new Bike("Bike", "Model1", BigDecimal.valueOf(500), "Manual");
       // Car car = new Car();
        Car car1 = new Car("Car", "Model1", BigDecimal.valueOf(30000), "Petrol", 5);
      //  Plane plane = new Plane();
        Plane plane1 = new Plane("Plane", "Model1", BigDecimal.valueOf(1000000), "Jet", 300, "Example Airlines");
      //  Truck truck = new Truck();
        Truck truck1 = new Truck("Truck", "Model1", BigDecimal.valueOf(50000), "Diesel", 2000.0);


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
