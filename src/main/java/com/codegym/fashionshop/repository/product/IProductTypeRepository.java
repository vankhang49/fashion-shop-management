package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Category;
import com.codegym.fashionshop.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing product types.
 * Includes custom queries for retrieving product types by category.
 * Author: HoaNTT
 */
@Repository
public interface IProductTypeRepository extends JpaRepository<ProductType, Long> {

    /**
     * Retrieves all product types belonging to a category by category name.
     *
     * @param categoryName the name of the category
     * @return a list of product types belonging to the specified category
     */
    @Query("SELECT pt FROM ProductType pt WHERE pt.category.categoryName = :categoryName")
    List<ProductType> findProductTypeByCategory_CategoryName(@Param("categoryName") String categoryName);

    /**
     * Retrieves all product types belonging to a category by category ID.
     *
     * @param categoryId the ID of the category
     * @return a list of product types belonging to the specified category
     */
    @Query("SELECT pt FROM ProductType pt WHERE pt.category.categoryId = :categoryId")
    List<ProductType> findProductTypeByCategory_CategoryId(@Param("categoryId") Long categoryId);
}
