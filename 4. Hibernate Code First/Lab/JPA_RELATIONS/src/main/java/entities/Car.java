package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends Vehicle{
        private static final String CAR_TYPE = "CAR";

        private int doorCount;


    public Car(int doorCount) {
        super(CAR_TYPE, DEFAULT_CAR_PRICE);
        this.doorCount = doorCount;
    }


    public static String getCarType(){
        return CAR_TYPE;
    }

    public int getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(int doorCount) {
        this.doorCount = doorCount;
    }
}
