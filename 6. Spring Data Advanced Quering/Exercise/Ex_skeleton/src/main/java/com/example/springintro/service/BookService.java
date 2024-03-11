package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllTitlesByAgeRestriction(String ageRestriction);

    List<String> findTitlesOfGoldenEditionBooksLessThan5000Copies();

    List<String> findTitlesAndPricesOfBooksInRange(float lowerPrice, float upperPrice);

    List<String> findTitlesOfBooksNotReleasedInYear(int year);

    List<Book> findBooksReleasedBeforeDate(String date);

    List<String> findBooksTitlesContainingTheGivenSting(String text);

    List<Book> findTitlesOfBooksWhoseAuthorLastNameStartsWith(String text);


    int countNumberOfBooksWhoseTitleIsLongerThan(int number);


}
