package com.example.advquerying.repositories;


import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
   List<Shampoo> findByBrand(String brand);

   List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findAllBySizeOrderById(Size size);

    //Това е JPQL заявка, която избира обекти от класа Shampoo (s), които са свързани със съставки (ingredients).
    // Използва се ключовата дума JOIN, за да се укаже, че трябва да се направи свързване между таблиците за Shampoo и Ingredient.
    // Последването на JOIN с s.ingredients AS i указва, че ще извършим връзка на таблицата Shampoo с таблицата на
    // съставките и ще я наричаме i. WHERE i.name IN :ingredientNames посочва условие, при което имената на съставките,
    // зададени в ingredientNames, се сравняват с имената на съставките в базата данни.
    @Query("SELECT s FROM Shampoo AS s JOIN s.ingredients AS i WHERE i.name IN :ingredientNames")
    List<Shampoo> findByIngredientsNames(Set<String> ingredientNames);


    List<Shampoo> findShampooByPriceGreaterThanOrderByPriceDesc(BigDecimal givenPrice);

    List<Shampoo> findBySizeOrLabelIdOrderByPriceAsc(Size size, Long labelId);

    int countAllByPriceLessThan(BigDecimal maxPrice);

    @Query("SELECT s FROM Shampoo s WHERE SIZE(s.ingredients) < :maxIngredients")
    List<Shampoo> findAllByIngredientsLessThan(@Param("maxIngredients") int maxIngredients);
}
