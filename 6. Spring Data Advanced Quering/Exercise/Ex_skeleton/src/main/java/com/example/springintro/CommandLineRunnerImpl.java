package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
      //  seedData();

        //printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
     //   printAllAuthorsAndNumberOfTheirBooks();
       // pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        /*
        Scanner scanner = new Scanner(System.in);
        String restriction = scanner.next();
        this.bookService.findAllTitlesByAgeRestriction(restriction)
                .forEach(System.out::println);
        */

        /*
        this.bookService
                .findTitlesOfGoldenEditionBooksLessThan5000Copies()
                .forEach(System.out::println);
         */

        /*
        this.bookService
                .findTitlesAndPricesOfBooksInRange(5, 40)
                .forEach(System.out::println);
         */

        /*
        List<String> books = bookService.findTitlesOfBooksNotReleasedInYear(1998);
        books.forEach(System.out::println);
        */

        /*
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        this.bookService.findBooksReleasedBeforeDate(date)
                .forEach(book -> System.out.printf("%s %s %.2f%n",
                        book.getTitle(),
                        book.getEditionType(),
                        book.getPrice()));
         */

        /*
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        this.authorService.findAuthorsNamesWhoseEndsWithGivenSting(text)
                .forEach(System.out::println);
       */

        /*
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        this.bookService.findBooksTitlesContainingTheGivenSting(text)
                .forEach(System.out::println);
         */

        /*
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        this.bookService.findTitlesOfBooksWhoseAuthorLastNameStartsWith(text)
                .forEach(book -> System.out.printf("%s ( %s %s )%n",
                        book.getTitle(),
                        book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()));
         */

        /*
        Scanner scanner = new Scanner(System.in);
        int number = Integer.parseInt(scanner.nextLine());
        int totalBooks = this.bookService.countNumberOfBooksWhoseTitleIsLongerThan(number);
        System.out.println(totalBooks);
         */

        Scanner scanner = new Scanner(System.in);
        String[]authorNames = scanner.nextLine().split("//s+");
        String firstN = authorNames[0];
        String lastN = authorNames[1];

       
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }

}
