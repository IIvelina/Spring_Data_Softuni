package bg.softuni.bookshopsystem.data.services;

import bg.softuni.bookshopsystem.data.entities.Category;

import java.io.IOException;

public interface CategoryService {
   
    void seedCategories() throws IOException;

    Category getCategoryById(Long id);
}
