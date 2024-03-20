package com.example.jsonex.productShop;

import com.example.jsonex.productShop.entities.products.ProductWithoutBuyerDTO;
import com.example.jsonex.productShop.services.ProductsService;
import com.example.jsonex.productShop.services.SeedService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductShopRunner implements CommandLineRunner {

    private final SeedService seedService;

    private final ProductsService productsService;

    private final Gson gson;

    //@Autowired
    public ProductShopRunner(SeedService seedService, ProductsService productsService) {
        this.seedService = seedService;
        this.productsService = productsService;

        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

    }

    @Override
    public void run(String... args) throws Exception {

       //this.seedService.seedUsers();
       // this.seedService.seedCategories();
       // this.seedService.seedProducts();

        List<ProductWithoutBuyerDTO> productsInPriceRangeForSell = this.productsService.getProductsInPriceRangeForSell(500, 1000);
        String json = this.gson.toJson(productsInPriceRangeForSell);

        System.out.println(json);


    }
}
