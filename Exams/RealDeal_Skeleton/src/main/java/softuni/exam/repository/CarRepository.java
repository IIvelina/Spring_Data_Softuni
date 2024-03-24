package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Car;

import java.util.List;

//ToDo
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByMake(String make);

    @Query("SELECT c FROM Car AS c ORDER BY size(c.pictures) DESC , c.make")
    List<Car> findCarsOrderByPicturesCountThenByMake();
}
