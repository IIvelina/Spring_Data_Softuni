package com.example.advquerying.services;


import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {


    List<Shampoo> selectBySizeAndLabelId(Size size, Long labelId);

    int countShampoosWithPriceLowerThan(BigDecimal maxPrice);

    List<Shampoo> selectShampoosByIngredientsCount(int maxIngredients);
}
