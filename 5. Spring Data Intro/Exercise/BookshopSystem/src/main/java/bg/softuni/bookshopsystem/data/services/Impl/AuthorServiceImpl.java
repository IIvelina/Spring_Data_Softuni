package bg.softuni.bookshopsystem.data.services.Impl;

import bg.softuni.bookshopsystem.data.constants.GlobalConstants;
import bg.softuni.bookshopsystem.data.entities.Author;
import bg.softuni.bookshopsystem.data.entities.Category;
import bg.softuni.bookshopsystem.data.repositories.AuthorRepository;
import bg.softuni.bookshopsystem.data.services.AuthorService;
import bg.softuni.bookshopsystem.data.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {

        if (this.authorRepository.count() != 0){
            return;
        }

        String[] fileContent = this.fileUtil
                .readFileContent(GlobalConstants.AUTHOR_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(r -> {
                        String[] params = r.split("\\s+");

                    Author author = new Author();

                    this.authorRepository.saveAndFlush(author);
                });


    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> findAllAuthorsByCountOfBooks() {
        return this.authorRepository.findAuthorByCountOfBooks();
    }
}
