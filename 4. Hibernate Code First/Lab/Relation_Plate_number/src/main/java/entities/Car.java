package entities;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car extends Vehicle{

    private Integer seats;

    @OneToOne(optional = false)
    @JoinColumn(name = "plateNumber_id",
    referencedColumnName = "id")
    private PlateNumbers plateNumber;

    public Car(Integer seats, PlateNumbers plateNumber) {
        this.seats = seats;
        this.plateNumber = plateNumber;
    }

    public Car(String type, String model, BigDecimal price, String fuelType, PlateNumbers plateNumber) {
        super(type, model, price, fuelType);
        this.plateNumber = plateNumber;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public PlateNumbers getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(PlateNumbers plateNumber) {
        this.plateNumber = plateNumber;
    }
}
