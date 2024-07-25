package com.codegym.fashionshop.service.product.impl;

import com.codegym.fashionshop.entities.ProductImage;
import com.codegym.fashionshop.repository.product.IProductImageRepository;
import com.codegym.fashionshop.service.product.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService implements IProductImageService {
    @Autowired
    private IProductImageRepository productImageRepository;

    @Override
    public ProductImage createImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public void deleteAllByProduct_ProductId(Long  id) {
        productImageRepository.deleteAllByProduct_ProductId(id);
    }
}
