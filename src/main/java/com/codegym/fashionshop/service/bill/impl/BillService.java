package com.codegym.fashionshop.service.bill.impl;


import com.codegym.fashionshop.entities.*;
import com.codegym.fashionshop.dto.DailyRevenueDTO;
import com.codegym.fashionshop.dto.SoldPricingsDTO;
import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.entities.Promotion;

import com.codegym.fashionshop.repository.bill.IBillRepository;
import com.codegym.fashionshop.repository.customer.ICustomerRepository;
import com.codegym.fashionshop.service.bill.IBillItemService;
import com.codegym.fashionshop.service.bill.IBillService;
import com.codegym.fashionshop.service.bill.IRequiredBillService;
import com.codegym.fashionshop.service.customer.ICustomerService;
import com.codegym.fashionshop.service.customer.ICustomerTypeService;
import com.codegym.fashionshop.service.promotion.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {
    @Autowired
    private IBillRepository repository;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IPromotionService promotionService;
    ;
    @Autowired
    private IRequiredBillService requiredBillService;

    @Autowired
    private ICustomerTypeService customerTypeService;
    /**
     * Creates a new bill and updates the customer's points.
     *
     * @param bill          The bill to create.
     * @param pointsToAdd   The number of points to add to the customer.
     * @author HoaNTT
     */
    @Transactional
    @Override
    public void createBillAndUpdatePoints(Bill bill, int pointsToAdd) {
        repository.save(bill);
        repository.updateAccumulatedPoints(bill.getCustomer().getCustomerId(), pointsToAdd);
    }
    /**
     * Finds all bills in the system.
     *
     * @return A list of all bills.
     * @author HoaNTT
     */
    @Override
    public List<Bill> findAll() {
        return repository.findAll();
    }
    /**
     * Finds all bills associated with a specific customer ID.
     *
     * @param customerId The ID of the customer.
     * @return A list of bills for the specified customer.
     * @author HoaNTT
     */
    @Override
    public List<Bill> findBillsByCustomer_CustomerId(Long customerId) {
        return repository.findBillsByCustomer_CustomerId(customerId);

    }
    /**
     * Checks if a given bill code is unique in the system.
     *
     * @param billCode The bill code to check.
     * @return True if the bill code is unique, false otherwise.
     * @author HoaNTT
     */
    public boolean isBillCodeUnique(String billCode) {
        return !repository.existsByBillCode(billCode);
    }
    /**
     * Calculates the total bill amount for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return The total bill amount for the customer.
     * @author HoaNTT
     */
    public Double calculateTotalBillForCustomer(Long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Bill> bills = repository.findBillsByCustomer_CustomerId(customerId);
        double totalAmount = 0.0;

        for (Bill bill : bills) {
            double billAmount = 0.0;
            for (BillItem item : bill.getBillItemList()) {
                billAmount += item.getPricing().getPrice() * item.getQuantity();
            }

            // Áp dụng mã khuyến mãi của hóa đơn nếu có
            if (bill.getPromotionCode() != null && !bill.getPromotionCode().isEmpty()) {
                Promotion promotion = promotionService.findByPromotionCode(bill.getPromotionCode());
                if (promotion != null) {
                        double discount = promotion.getDiscount();
                    if (discount > 0 && discount < 1) {
                        // Tính theo phần trăm trên tổng bill
                        billAmount -= billAmount * discount;
                    } else if (discount >= 1) {
                        // Lấy tổng bill trừ ra
                        billAmount -= discount;
                    }
                }
            }
            // Áp dụng giảm giá theo loại khách hàng
            if (customer.getCustomerType() != null) {
                billAmount -= billAmount * customer.getCustomerType().getDiscount();
            }

            totalAmount += billAmount;
        }

        return totalAmount;
    }

    /**
     * Updates the customer type based on the total amount spent by the customer.
     *
     * @param bill The bill associated with the customer.
     * @author HoaNTT
     */
    public void updateCustomerTypeOfCustomer(Bill bill) {
        // Tính tổng số tiền từ trước đến giờ của khách hàng sau khi tạo hóa đơn mới
        double totalAmount = this.calculateTotalBillForCustomer(bill.getCustomer().getCustomerId());
        System.out.println(totalAmount);
        // Kiểm tra tổng số tiền với bảng RequiredBill để nâng cấp loại khách hàng
        List<RequiredBill> requiredBills = requiredBillService.findAll();
        CustomerType newCustomerType = null;

        for (RequiredBill requiredBill : requiredBills) {
            if (totalAmount >= requiredBill.getTypePrice()) {
                CustomerType customerType = customerTypeService.findById(requiredBill.getTypeId());
                if (newCustomerType == null || customerType.getDiscount() > newCustomerType.getDiscount()) {
                    newCustomerType = customerType;
                    System.out.println(newCustomerType.getTypeName());
                }
            }
        }
        // Nâng cấp loại khách hàng nếu tìm thấy loại phù hợp
        if (newCustomerType != null && !newCustomerType.equals(bill.getCustomer().getCustomerType())) {
            bill.getCustomer().setCustomerType(newCustomerType);
            System.out.println("nâng cấp ");
            repository.updateCustomerTypeOfCustomer(bill.getCustomer().getCustomerId(),newCustomerType.getTypeId());
        }

    }

    /**
     * {@inheritDoc}
     *
     * @author ThanhTT
     */
    @Override
    public Double getDailySalesRevenue(LocalDate date) {
        return repository.getDailySalesRevenue(date);
    }

    /**
     * {@inheritDoc}
     *
     * @author ThanhTT
     */
    @Override
    public Double getMonthlySalesRevenue(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int monthValue = yearMonth.getMonthValue();
        return repository.getMonthlySalesRevenue(year, monthValue);
    }

    /**
     * {@inheritDoc}
     *
     * @author ThanhTT
     */
    @Override
    public List<DailyRevenueDTO> getDailySalesRevenueForMonth(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int monthValue = yearMonth.getMonthValue();
        List<DailyRevenueDTO> revenueDTOList = new ArrayList<>();
        List<Object[]> results = repository.getDailySalesRevenueForMonth(year, monthValue);
        for(Object[] result: results) {
            int day = (Integer) result[0];
            double revenue = (Double) result[1];
            DailyRevenueDTO dailyRevenue = new DailyRevenueDTO(day, revenue);
            revenueDTOList.add(dailyRevenue);
        }
        return revenueDTOList;
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public List<SoldPricingsDTO> getDailySoldPricings(LocalDate date) {
        List<Object[]> results = repository.getDailySoldPricings(date);
        return results.stream().map(this::mapToSoldPricings).collect(Collectors.toList());
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public List<SoldPricingsDTO> getDailySoldPricings(int year, int month) {
        List<Object[]> results = repository.getMonthlySoldPricings(year, month);
        return results.stream().map(this::mapToSoldPricings).collect(Collectors.toList());
    }
    private SoldPricingsDTO mapToSoldPricings(Object[] object) {
        return SoldPricingsDTO.builder()
                .pricingCode((String) object[0])
                .pricingName((String) object[1])
                .totalQuantity( ( (BigDecimal) object[2] ).intValue() )
                .price((double) object[3])
                .build();
    }
}
