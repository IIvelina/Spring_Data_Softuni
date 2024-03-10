package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {
    @Autowired
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }


    @Override
    public List<Shampoo> selectBySizeAndLabelId(Size size, Long labelId) {
        //Label label = this.labelRepository.findById(labelId);

        return this.shampooRepository.findBySizeOrLabelIdOrderByPriceAsc(size, labelId);
    }

    @Override
    public int countShampoosWithPriceLowerThan(BigDecimal maxPrice) {
        return shampooRepository.countAllByPriceLessThan(maxPrice);
    }

    @Override
    public List<Shampoo> selectShampoosByIngredientsCount(int maxIngredients) {
        return this.shampooRepository.findAllByIngredientsLessThan(maxIngredients);
    }
}
