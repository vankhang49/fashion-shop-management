package com.codegym.fashionshop.service.dashboard;

import com.codegym.fashionshop.dto.BestSalespersonDTO;
import com.codegym.fashionshop.repository.dashboard.BestSalespersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BestSalespersonService {
    @Autowired
    private BestSalespersonRepository bestSellerRepository;

    public List<BestSalespersonDTO> getBestSalespersons() {
        return bestSellerRepository.getBestSalespersons();
    }
}
