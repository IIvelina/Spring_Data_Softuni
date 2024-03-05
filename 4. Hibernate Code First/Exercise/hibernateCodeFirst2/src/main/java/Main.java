import entities.Customer;
import entities.Product;
import entities.Sale;
import entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("PU_Name");

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        // Създаване на обекти от класовете Product, Customer и StoreLocation
        Product product = new Product();
        product.setName("Laptop");
        product.setQuantity(10);
        product.setPrice(BigDecimal.valueOf(1500));

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setCreditCardNumber("1234567890123456");

        StoreLocation storeLocation = new StoreLocation();
        storeLocation.setLocationName("New York");

        // Създаване на обект от клас Sale и свързване с Product, Customer и StoreLocation
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setCustomer(customer);
        sale.setStoreLocation(storeLocation);
        sale.setDate(new Date());

        // Запазване на обектите в базата данни
        entityManager.persist(product);
        entityManager.persist(customer);
        entityManager.persist(storeLocation);
        entityManager.persist(sale);



        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }
}
