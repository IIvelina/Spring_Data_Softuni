package bg.softuni.bookshopsystem.data.services;

import bg.softuni.bookshopsystem.data.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBooksAfter2000();


}
