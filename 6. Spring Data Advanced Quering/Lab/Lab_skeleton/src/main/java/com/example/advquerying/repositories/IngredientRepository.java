package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


    List<Ingredient> findAllByNameStartingWith(String later);

    List<Ingredient> findAllByNameInOrderByPriceAsc(List<String> names);

    int deleteByName(String name);

}
