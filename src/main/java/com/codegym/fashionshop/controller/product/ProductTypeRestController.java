package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.ProductType;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing product types.
 * Provides endpoints for retrieving all product types and handling exceptions related to product type operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/auth/productType")
public class ProductTypeRestController {

    @Autowired
    private IProductTypeService productTypeService;

    /**
     * GET endpoint to retrieve all product types.
     *
     * @return a ResponseEntity containing a list of all product types and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no product types are found
     */
    @GetMapping
    public ResponseEntity<List<ProductType>> getAllProduct() {
        List<ProductType> productTypes = productTypeService.findAll();
        if (productTypes.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin danh mục sản phẩm");
        }
        return new ResponseEntity<>(productTypes, HttpStatus.OK);
    }
}
