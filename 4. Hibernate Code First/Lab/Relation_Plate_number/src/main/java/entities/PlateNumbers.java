package entities;

import javax.persistence.*;

@Entity
@Table(name = "plate_number")
public class PlateNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private Long id;
    @Column(name = "number", length = 255)
    private String number;

    public PlateNumbers() {
    }

    public PlateNumbers(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
