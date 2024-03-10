package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> selectIngredientsByName(String later);

    List<Ingredient> selectIngredientsInListSortedByPrice(List<String> ingredientNames);


    int deleteByName(String name);
}
