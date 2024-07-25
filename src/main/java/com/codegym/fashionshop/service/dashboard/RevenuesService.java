package com.codegym.fashionshop.service.dashboard;

import com.codegym.fashionshop.dto.RevenueDTO;
import com.codegym.fashionshop.repository.dashboard.IRevenuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenuesService {
    @Autowired
    private IRevenuesRepository revenueRepository;

    public RevenueDTO getRevenueData(int option) {
        return revenueRepository.getTotalRevenueAndGrowth(option);
    }

}
