package com.codegym.fashionshop.service.dashboard;

import com.codegym.fashionshop.dto.TotalBillDTO;
import com.codegym.fashionshop.repository.dashboard.ITotalBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalBillService {
    @Autowired
    private ITotalBillRepository totalBillRepository;

    public TotalBillDTO getTotalBillAndGrowth() {
        return totalBillRepository.getTotalBillAndGrowth();
    }
}
