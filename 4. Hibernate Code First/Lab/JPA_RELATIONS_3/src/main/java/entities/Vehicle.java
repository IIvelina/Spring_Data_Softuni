package entities;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//Когато клас е обявен като "abstract", той не може да бъде инстанцииран направо.
// Това означава, че не можем да създадем обект от абстрактния клас. Вместо това, абстрактният
// клас се използва като базов клас за други класове, които ще наследяват от него.
@DiscriminatorColumn(name = "type")
public abstract class Vehicle {
    public static final double DEFAULT_CAR_PRICE = 2500.0;
    public static final double DEFAULT_BIKE_PRICE = 250.0;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(insertable = false, updatable = false)
    private String type;

    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Vehicle(String type, Double price) {
        this.type = type;
        this.price = price;
    }

    public Vehicle() {
    }

    public Vehicle(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
