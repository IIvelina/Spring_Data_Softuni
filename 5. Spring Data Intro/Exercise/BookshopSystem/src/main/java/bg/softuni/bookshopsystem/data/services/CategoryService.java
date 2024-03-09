package bg.softuni.bookshopsystem.data.services;

import bg.softuni.bookshopsystem.data.entities.Category;

import java.io.IOException;

public interface CategoryService {
    //нашия контролер ще каже CategoryService seed Categories
    //и нашия CategoryService ще си намери файла, ще прочете всичко с FileUtil
    //ще създаде нови категории и ще ги сложи в базата
    void seedCategories() throws IOException;

    Category getCategoryById(Long id);
}
