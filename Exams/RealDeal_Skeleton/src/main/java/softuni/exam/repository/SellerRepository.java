package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Rating;
import softuni.exam.models.Seller;

import java.util.Optional;

//ToDo
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByFirstNameAndLastNameAndEmailAndTownAndRating(String firstName, String lastName, String email, String town, Rating rating);
}
