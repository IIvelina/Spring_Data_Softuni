package entities;
import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "students")
public class Student extends BasicInfoPerson{
    @Column(name = "average_grade", nullable = false)
    private double averageGrade;
    private int attendance;

    @ManyToMany
    private Set<Course> courses;

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String phoneNumber, double averageGrade, int attendance) {
        super(firstName, lastName, phoneNumber);
        this.averageGrade = averageGrade;
        this.attendance = attendance;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
}
