package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage,Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImage p WHERE p.product.productId = :productId")
    void deleteAllByProduct_ProductId(@Param("productId") Long productId);

}
