package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Color;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for managing color-related operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/auth/color")
public class ColorRestController {

    @Autowired
    private IColorService colorService;

    /**
     * GET endpoint to retrieve all colors.
     *
     * @return a ResponseEntity containing a list of colors and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no colors are found
     */
    @GetMapping
    public ResponseEntity<List<Color>> getColor() {
        List<Color> colors = colorService.findAllColor();
        if (colors.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin màu sắc");
        }
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }
}
