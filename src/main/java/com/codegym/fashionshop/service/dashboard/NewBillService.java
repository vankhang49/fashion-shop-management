package com.codegym.fashionshop.service.dashboard;

import com.codegym.fashionshop.dto.NewBillDTO;
import com.codegym.fashionshop.repository.dashboard.NewBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewBillService {
    @Autowired
    private NewBillRepository newBillRepository;

    public List<NewBillDTO> getAllNewBills() {
        return newBillRepository.getNewBillDTOList();
    }
}
