package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bikes")
public class Bike extends Vehicle{
    private static final String BIKE_TYPE = "BIKE";

    private int gearsCount;

    public Bike(int gearsCount) {
        super(BIKE_TYPE, DEFAULT_BIKE_PRICE);
        this.gearsCount = gearsCount;
    }

    public Bike() {

    }

    public static String getBikeType(){
        return BIKE_TYPE;
    }


    public int getGearsCount() {
        return gearsCount;
    }

    public void setGearsCount(int gearsCount) {
        this.gearsCount = gearsCount;
    }


}

