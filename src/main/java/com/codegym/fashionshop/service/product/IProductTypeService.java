package com.codegym.fashionshop.service.product;

import com.codegym.fashionshop.entities.ProductType;

import java.util.List;

public interface IProductTypeService {
    List<ProductType> findProductTypeByCategory_CategoryName(String category_categoryName);
    List<ProductType> findProductTypeByCategory_CategoryId(Long category_categoryId);
    List<ProductType> findAll();

}
