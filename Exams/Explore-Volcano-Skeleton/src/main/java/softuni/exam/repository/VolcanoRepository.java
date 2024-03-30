package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;

import java.util.List;
import java.util.Optional;

// TODO:
@Repository
public interface VolcanoRepository extends JpaRepository<Volcano, Long> {

    Optional<Volcano> findByName(String name);

    Optional<Volcano> findById(Long id);
    @Query("SELECT v.name, v.country.name, v.elevation, v.lastEruption FROM Volcano v " +
            "WHERE v.isActive = true AND v.elevation > 3000 AND v.lastEruption IS NOT NULL " +
            "ORDER BY v.elevation DESC")
    List<Object[]> findActiveVolcanoesAbove3000mWithLastEruption();
}
