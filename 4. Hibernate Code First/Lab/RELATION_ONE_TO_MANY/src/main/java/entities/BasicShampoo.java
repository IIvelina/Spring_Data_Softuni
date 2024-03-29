package entities;

import javax.persistence.*;

@Entity
@Table(name = "shampoos")
public class BasicShampoo  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @OneToOne(optional = false)
    @JoinColumn (name = "label_id", referencedColumnName = "id")
    private BasicLabel label;

    @ManyToOne(optional = false)
    @JoinColumn(name = "batch_id", referencedColumnName = "id")

    private ProductionBatch batch;


    public BasicShampoo() {
    }

    public BasicShampoo(String name, BasicLabel label) {
        this.name = name;
        this.label = label;
    }

    public BasicShampoo(String name, BasicLabel label, ProductionBatch batch) {
        this.name = name;
        this.label = label;
        this.batch = batch;
    }

    public ProductionBatch getBatch() {
        return batch;
    }

    public void setBatch(ProductionBatch batch) {
        this.batch = batch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BasicLabel getLabel() {
        return label;
    }

    public void setLabel(BasicLabel label) {
        this.label = label;
    }
}