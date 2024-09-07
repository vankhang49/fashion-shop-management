package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Category;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing category-related operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/auth/category")
public class CategoryRestController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * GET endpoint to retrieve all categories.
     *
     * @return a ResponseEntity containing a list of categories and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no categories are found
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        if (categories.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin danh mục sản phẩm");
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
