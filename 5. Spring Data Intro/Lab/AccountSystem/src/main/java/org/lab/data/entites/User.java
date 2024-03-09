package org.lab.data.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    //Username – unique for each user
    @Column(unique = true)
    private String username;

    //Age – integer value
    @Column
    private int age;

    //	Accounts – each user can have many accounts, which will be identified by their id
    @OneToMany(mappedBy = "user")
    private Set<Account> account; // this.account = new HashSet<>();!!!!!!!

    public User() {
        //!!!!!!!!!
        this.account = new HashSet<>();
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
        this.account = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Account> getAccount() {
        return account;
    }

    public void setAccount(Set<Account> account) {
        this.account = account;
    }
}
