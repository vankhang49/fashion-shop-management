package com.codegym.fashionshop.service.product;

import com.codegym.fashionshop.entities.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAllCategories();
}
