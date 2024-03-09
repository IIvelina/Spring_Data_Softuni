package bg.softuni.bookshopsystem.data.controllers;

import bg.softuni.bookshopsystem.data.entities.Author;
import bg.softuni.bookshopsystem.data.entities.Book;
import bg.softuni.bookshopsystem.data.services.AuthorService;
import bg.softuni.bookshopsystem.data.services.BookService;
import bg.softuni.bookshopsystem.data.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

        //Get all books after the year 2000. Print only their titles.
       // List<Book> books = this.bookService.getAllBooksAfter2000();

        //Get all authors, ordered by the number of their books (descending).
        // Print their first name, last name and book count.
        List<Author> authors = this.authorService.findAllAuthorsByCountOfBooks();

    }
}
