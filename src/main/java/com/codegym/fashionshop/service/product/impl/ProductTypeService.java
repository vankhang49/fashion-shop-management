package com.codegym.fashionshop.service.product.impl;

import com.codegym.fashionshop.entities.ProductType;
import com.codegym.fashionshop.repository.product.IProductTypeRepository;
import com.codegym.fashionshop.service.product.IProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService implements IProductTypeService {
    @Autowired
    private IProductTypeRepository productTypeRepository;
    @Override
    public List<ProductType> findProductTypeByCategory_CategoryName(String category_categoryName) {
        return productTypeRepository.findProductTypeByCategory_CategoryName(category_categoryName);
    }

    @Override
    public List<ProductType> findProductTypeByCategory_CategoryId(Long category_categoryId) {
        return productTypeRepository.findProductTypeByCategory_CategoryId(category_categoryId);
    }

    @Override
    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }
}
