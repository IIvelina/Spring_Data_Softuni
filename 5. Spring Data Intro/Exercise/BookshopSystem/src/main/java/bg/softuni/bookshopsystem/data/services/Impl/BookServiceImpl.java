package bg.softuni.bookshopsystem.data.services.Impl;

import bg.softuni.bookshopsystem.data.constants.GlobalConstants;
import bg.softuni.bookshopsystem.data.entities.*;
import bg.softuni.bookshopsystem.data.repositories.BookRepository;
import bg.softuni.bookshopsystem.data.services.AuthorService;
import bg.softuni.bookshopsystem.data.services.BookService;
import bg.softuni.bookshopsystem.data.services.CategoryService;
import bg.softuni.bookshopsystem.data.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;

    private final AuthorService authorService;

    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {

        if (this.bookRepository.count() != 0){
            return;
        }

        String[] fileContent = this.fileUtil
                .readFileContent(GlobalConstants.BOOK_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(row -> {
                    String[] data = row.split("\\s+");
                    Author author = this.getRandomAuthor();

                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate = LocalDate.parse(data[1], formatter);
                    /*
                    LocalDate releaseDate = LocalDate.parse(data[1],
                            DateTimeFormatter.ofPattern("d/M/yyyy"));
                     */

                    int copies = Integer.parseInt(data[2]);

                    BigDecimal price = new BigDecimal(data[3]);

                    AgeRestriction ageRestriction = AgeRestriction
                            .values()[Integer.parseInt(data[4])];

                    /*
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                     */

                    String title = this.getTitle(data);

                    Set<Category> categories = this.getRandomCategories();


                    Book book = new Book();
                    book.setAuthor(author);
                    book.setEditionType(editionType);
                    book.setReleaseDate(releaseDate);
                    book.setCopies(copies);
                    book.setPrice(price);
                    book.setAgeRestriction(ageRestriction);
                    book.setTitle(title);
                    book.setCategories(categories);

                    this.bookRepository.saveAndFlush(book);
                });
    }

    @Override
    public List<Book> getAllBooksAfter2000() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate releaseDate = LocalDate.parse("31/12/2000", formatter);
        return this.bookRepository
                .findAllByReleaseDateAfter(releaseDate);
    }

    private Set<Category> getRandomCategories() {
        Random random = new Random();
        int bound = random.nextInt(3) +1;
        Set<Category> result = new HashSet<>();
        for (int i = 1; i <= bound; i++) {
            int categoryId = random.nextInt(8) + 1;
            result.add(this.categoryService.getCategoryById((long) categoryId));
        }
        return result;
    }

    private String getTitle(String[] data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 5; i < data.length; i++) {
            sb.append(data[i]).append(" ");
        }
        return sb.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;

        return this.authorService.findAuthorById((long) randomId);
    }
}
