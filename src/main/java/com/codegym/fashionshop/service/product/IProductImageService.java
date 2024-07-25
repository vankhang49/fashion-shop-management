package com.codegym.fashionshop.service.product;

import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.entities.ProductImage;
import com.codegym.fashionshop.repository.product.IProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface IProductImageService {
    ProductImage createImage(ProductImage productImage);
    void deleteAllByProduct_ProductId(Long  id);


}
