package org.lab.data.repositories;

import org.lab.data.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//приема User и нашето id -> Integer
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
