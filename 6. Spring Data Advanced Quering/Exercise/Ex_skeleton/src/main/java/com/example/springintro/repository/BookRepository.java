package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);


    List<Book> findByAgeRestriction(AgeRestriction restriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);



    //List<Book> findAllByPriceLessThanAndPriceGreaterThan(BigDecimal lowerPrice, BigDecimal upperPrice);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerPrice, BigDecimal upperPrice);

    @Query("SELECT b FROM Book AS b WHERE EXTRACT(YEAR FROM b.releaseDate) != :year")
    List<Book> findAllByReleaseDateYearNot(int year);

    @Query("SELECT b FROM Book b WHERE b.releaseDate < :date")
    List<Book> findAllByReleaseDateBefore(@Param("date") Date date);

    List<Book> findAllByTitleContainingIgnoreCase(String text);

    List<Book> findAllByAuthorLastNameStartingWithIgnoreCase(String text);

    @Query("SELECT COUNT(b) FROM Book AS b WHERE length(b.title) > :length ")
    int countBookWithTitleLongerThan(@Param("length") int number);


}

