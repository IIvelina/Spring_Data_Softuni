package entities;

import javax.persistence.MappedSuperclass;

//@MappedSuperclass -> това е анутация, която ни указва свойствата този клас
//ще бъдат записани към конкретния
//не е нужно да правим отделна таблица, само за този абртрактен, междинен клас

//която се използва за обозначаване на клас като базов клас за JPA съответните класове,
//без да се създава таблица за базовия клас в базата данни.
//В същност, когато анотираме клас с @MappedSuperclass, това означава, че класът ще съдържа
// общи полета и методи, които могат да бъдат споделени от няколко класа, които наследяват
// от него, но също така този клас няма да се превърне в таблица в базата данни.
@MappedSuperclass
public abstract class TransportationVehicle extends Vehicle{
    private int loadCapacity;

    public TransportationVehicle(){ }


    public TransportationVehicle(String type, Double price, int loadCapacity) {
        super(type, price);
        this.loadCapacity = loadCapacity;
    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
