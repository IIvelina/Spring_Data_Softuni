package entities;



import javax.persistence.Entity;

import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle{

    //@Column(name = "passenger_capacity", columnDefinition = "int(11)")
    private Integer passengerCapacity;
   // @Column(name = "airline", length = 255)
    private String airline;

    public Plane() {

    }

    public Plane(Integer passengerCapacity, String airline) {
        this.passengerCapacity = passengerCapacity;
        this.airline = airline;
    }

    public Plane(String type, String model, BigDecimal price, String fuelType, Integer passengerCapacity, String airline) {
        super(type, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
        this.airline = airline;
    }



    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
