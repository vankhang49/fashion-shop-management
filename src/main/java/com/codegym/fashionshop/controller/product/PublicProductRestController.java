package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/public/product")
public class PublicProductRestController {

    @Autowired
    private IProductService iProductService;

    @GetMapping("/new")
    public ResponseEntity<Page<Product>> getAllProductNew(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,@RequestParam(name = "page", defaultValue = "0") int page) {

        final int MAX_PAGES = 2;
        final int PAGE_SIZE = 5;

        if (page < 0) {
            page = 0;
        }
        if (page >= MAX_PAGES) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Page<Product> products = iProductService.searchAndSortProducts(keyword,PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "productId")));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/nam-nu")
    public ResponseEntity<Page<Product>> getAllProductGender(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Product> products = iProductService.searchAndSortProducts(keyword,PageRequest.of(page, 10));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = iProductService.findProductById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> getAllProduct(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        }
        Page<Product> products = iProductService.searchAndSortProducts(keyword, PageRequest.of(page, 10, sort));
        if (products.isEmpty()) {
            products = Page.empty();
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>( products,HttpStatus.OK);
    }

}
