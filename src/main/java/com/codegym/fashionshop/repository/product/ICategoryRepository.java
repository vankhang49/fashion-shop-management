package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing categories.
 * Includes a custom query for retrieving all categories.
 * Author: HoaNTT
 */
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    @Query("SELECT c FROM Category c")
    List<Category> findAll();
}
