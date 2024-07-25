package com.codegym.fashionshop.service.product.impl;

import com.codegym.fashionshop.entities.Category;
import com.codegym.fashionshop.repository.product.ICategoryRepository;
import com.codegym.fashionshop.service.product.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
