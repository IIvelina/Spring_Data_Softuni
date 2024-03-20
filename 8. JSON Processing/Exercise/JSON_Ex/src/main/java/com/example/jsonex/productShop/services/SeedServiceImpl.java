package com.example.jsonex.productShop.services;

import com.example.jsonex.productShop.entities.products.Product;
import com.example.jsonex.productShop.entities.categories.Category;
import com.example.jsonex.productShop.entities.categories.CategoryImportDTO;
import com.example.jsonex.productShop.entities.products.ProductImportDTO;
import com.example.jsonex.productShop.entities.users.User;
import com.example.jsonex.productShop.entities.users.UserImportDTO;
import com.example.jsonex.productShop.repositories.CategoryRepository;
import com.example.jsonex.productShop.repositories.ProductRepository;
import com.example.jsonex.productShop.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String USERS_JSON_PATH = "src/main/resources/productShop/users.json";
    private static final String CATEGORIES_JSON_PATH = "src/main/resources/productShop/categories.json";
    private static final String PRODUCTS_JSON_PATH = "src/main/resources/productShop/products.json";
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(USERS_JSON_PATH);
        UserImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, UserImportDTO[].class);

        List<User> users = Arrays.stream(userImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, User.class))
                .collect(Collectors.toList());

        this.userRepository.saveAll(users);
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CATEGORIES_JSON_PATH);
        CategoryImportDTO[] categoryImportDTOS = this.gson.fromJson(fileReader, CategoryImportDTO[].class);

        List<Category> categories = Arrays.stream(categoryImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, Category.class))
                .collect(Collectors.toList());

        this.categoryRepository.saveAll(categories);
    }

    @Override
    public void seedProducts() throws FileNotFoundException {

        FileReader fileReader = new FileReader(PRODUCTS_JSON_PATH);
        ProductImportDTO[] productImportDTOS = this.gson.fromJson(fileReader, ProductImportDTO[].class);

        List<Product> products = Arrays.stream(productImportDTOS)
                .map(importDTO -> this.modelMapper.map(importDTO, Product.class))
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .map(this::sendRandomCategories)
                .collect(Collectors.toList());

        this.productRepository.saveAll(products);
    }

    private Product sendRandomCategories(Product product) {
        Random random = new Random();
        long categoriesDbCount = this.categoryRepository.count();
        int count = random.nextInt((int)categoriesDbCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int randomId = random.nextInt((int)categoriesDbCount) + 1;
            Optional<Category> randomCategory = this.categoryRepository.findById(randomId);
            categories.add(randomCategory.get());
        }
        product.setCategories(categories);
        return product;
    }

    private Product setRandomSeller(Product product){
        long usersCount = this.userRepository.count();

        int randomUserId = new Random().nextInt((int) usersCount) + 1;
        Optional<User> seller = this.userRepository.findById(randomUserId);

        product.setSeller(seller.get());

        return product;
    }

    private Product setRandomBuyer(Product product){
        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0){
            return product;
        }

        Optional<User> buyer = getRandomUser();
        product.setBuyer(buyer.get());

        return product;
    }

    private Optional<User> getRandomUser(){
        long usersCount = this.userRepository.count();
        int randomUserId = new Random().nextInt((int) usersCount) + 1;
        Optional<User> seller = this.userRepository.findById(randomUserId);
        return seller;
    }
}
