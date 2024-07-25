package com.codegym.fashionshop.service.product;


import com.codegym.fashionshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IProductService {
    void createProduct(Product product);

    List<Product> findAllProduct();

    Page<Product> findAllProduct(Pageable pageable);

    boolean isProductCodeUnique(String productCode);

    Page<Product> searchAndSortProducts(String keyword, Pageable pageable);

    Product findProductById(Long id);

    void save(Product product);

    void updateProduct(Long productId, Product product);

    void deleteProduct(Long productId,Boolean enabled);
}