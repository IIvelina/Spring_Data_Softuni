package entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "trucks")
@DiscriminatorValue("truck")
public class Truck extends TransportationVehicle{
    private final static String TRUCK_TYPE = "TRUCK";
    //private int numOfContainers;


    public Truck(Double price, int loadCapacity) {
        super(TRUCK_TYPE, price, loadCapacity);
    }

    public Truck() {

    }
}
