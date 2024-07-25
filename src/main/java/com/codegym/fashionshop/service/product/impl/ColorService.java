package com.codegym.fashionshop.service.product.impl;

import com.codegym.fashionshop.entities.Color;
import com.codegym.fashionshop.repository.product.IColorRepository;
import com.codegym.fashionshop.service.product.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService implements IColorService {
    @Autowired
    private IColorRepository colorRepository;
    @Override
    public void createColor(Color color) {

    }

    @Override
    public List<Color> findAllColor() {
        return colorRepository.findAll();
    }
}
