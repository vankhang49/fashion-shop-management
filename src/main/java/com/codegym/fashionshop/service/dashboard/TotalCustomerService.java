package com.codegym.fashionshop.service.dashboard;

import com.codegym.fashionshop.dto.TotalCustomerDTO;
import com.codegym.fashionshop.repository.dashboard.ITotalCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalCustomerService {
    @Autowired
    private ITotalCustomerRepository totalCustomerRepository;

    public TotalCustomerDTO getTotalCustomerAndGrowth() {
        return totalCustomerRepository.getTotalCustomerAndGrowth();
    }
}
