package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "batches")
public class ProductionBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "batch", targetEntity = BasicShampoo.class,
    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BasicShampoo> shampooSet;

    public ProductionBatch(LocalDate createdAt, Set<BasicShampoo> shampooSet) {
        this.createdAt = createdAt;
        this.shampooSet = shampooSet;
    }

    public ProductionBatch() {
    }

    public ProductionBatch(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
