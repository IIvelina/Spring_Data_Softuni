package com.example.advquerying;

import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    private final ShampooRepository shampooRepository;

    private final ShampooService shampooService;

    private final IngredientService ingredientService;

    @Autowired
    public Runner(ShampooRepository shampooRepository, ShampooService shampooService, IngredientService ingredientService) {
        this.shampooRepository = shampooRepository;
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override

    public void run(String... args) throws Exception {
        /*
        shampooRepository.findByBrand("Cotton Fresh")
                .forEach(shampoo -> System.out.println(shampoo.getId()));
         */

        /*
        shampooRepository.findByBrandAndSize("Keratin Strong", Size.LARGE)
                .forEach(shampoo -> System.out.println(shampoo.getId()));
         */


        Scanner scanner = new Scanner(System.in);
        /*
        String sizeName = scanner.nextLine();
        Size size = Size.valueOf(sizeName);

        shampooRepository.findAllBySizeOrderById(size).forEach(System.out::println);
         */

        /*
        String first = scanner.nextLine();
        String second = scanner.nextLine();
        Set<String> names = Set.of(first, second);
        this.shampooRepository.findByIngredientsNames(names)
                .forEach(System.out::println);
         */

        /*
        BigDecimal price = scanner.nextBigDecimal();
        shampooRepository.findShampooByPriceGreaterThanOrderByPriceDesc(price)
                .forEach(System.out::println);
         */

        /*
        Size size = Size.MEDIUM;
        Long labelId = 10L;
        this.shampooService.selectBySizeAndLabelId(size, labelId)
                .forEach(System.out::println);
        */

        /*
        String later = "M";
        this.ingredientService.selectIngredientsByName(later)
                .forEach(ingredient -> System.out.println(ingredient.getName()));
         */

        /*
        List<String> ingredientNames = Arrays.asList("Lavender", "Herbs", "Apple");

        ingredientService.selectIngredientsInListSortedByPrice(ingredientNames)
                .forEach(ingredient -> System.out.println(ingredient.getName()));
        */

        /*
        BigDecimal maxPrice = new BigDecimal("8.50");
        int count = shampooService.countShampoosWithPriceLowerThan(maxPrice);
        System.out.println("Number of shampoos with price lower than " + maxPrice + ": " + count);
        */

        /*
        int maxIngredients = 2;
        List<Shampoo> shampoos = shampooService.selectShampoosByIngredientsCount(maxIngredients);
        System.out.println("Shampoos with ingredients less than " + maxIngredients + ":");
        for (Shampoo shampoo : shampoos) {
            System.out.println(shampoo);
        }
         */

        //  @Transactional !!!!!!
        this.ingredientService.deleteByName("Nettle");
    }
}
