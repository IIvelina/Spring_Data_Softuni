package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CarSeedDto implements Serializable {
    @Expose
    @NotNull
    @Size(min = 2, max = 20)
    private String make;
    @Expose
    @NotNull
    @Size(min = 2, max = 20)
    private String model;
    @Expose
    @NotNull
    @Min(0)
    private int kilometers;
    @Expose
    @NotNull
    private String registeredOn;

    public String getMake() {
        return make;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }


}
