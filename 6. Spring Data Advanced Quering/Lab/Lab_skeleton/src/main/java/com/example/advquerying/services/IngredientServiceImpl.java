package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class IngredientServiceImpl implements IngredientService{


    @Autowired
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> selectIngredientsByName(String later) {
        return this.ingredientRepository.findAllByNameStartingWith(later);
    }

    @Override
    public List<Ingredient> selectIngredientsInListSortedByPrice(List<String> ingredientNames) {
        return ingredientRepository.findAllByNameInOrderByPriceAsc(ingredientNames);
    }

    @Override
    @Transactional
    public int deleteByName(String name) {
        return this.ingredientRepository.deleteByName(name);
    }


}
