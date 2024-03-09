package bg.softuni.bookshopsystem.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Book> book;

    public Category() {
        this.book = new HashSet<>();
    }

    public Category(String name) {
        this.name = name;
        this.book = new HashSet<>();
    }

    public Category(String name, Set<Book> book) {
        this.name = name;
        this.book = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBook() {
        return book;
    }

    public void setBook(Set<Book> book) {
        this.book = book;
    }
}
