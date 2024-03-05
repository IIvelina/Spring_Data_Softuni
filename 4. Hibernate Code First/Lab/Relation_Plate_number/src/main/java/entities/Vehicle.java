package entities;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
//@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private Long id;
    //@Column(name = "type", length = 255)
    @Basic
    private String type;
   // @Column(name = "model", length = 255)
   @Basic
    private String model;
    //@Column(name = "price", precision = 19, scale = 2)
    @Basic
    private BigDecimal price;
    //@Column(name = "fuel_type", length = 255)
    @Basic
    private String fuelType;

    public Vehicle() {
    }

    public Vehicle(String type, String model, BigDecimal price, String fuelType) {
        this.type = type;
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
