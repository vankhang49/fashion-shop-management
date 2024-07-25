package com.codegym.fashionshop.service.product;

import com.codegym.fashionshop.entities.Color;
import com.codegym.fashionshop.entities.Product;

import java.util.List;

public interface IColorService {
    void createColor(Color color);
    List<Color> findAllColor();
}
