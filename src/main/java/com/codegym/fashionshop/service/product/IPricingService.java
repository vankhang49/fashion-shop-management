package com.codegym.fashionshop.service.product;

import com.codegym.fashionshop.dto.WarehouseReceiptDTO;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.repository.product.IPricingRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IPricingService  {
    List<Pricing> findAllPricing();
    Page<Pricing> findAllPricing(Pageable pageable);
    Page<Pricing> findAllByProduct_ProductId(Long productId,Pageable pageable);

    void createPricing(Pricing pricing);

    /**
     * Updates the pricing quantities based on the provided warehouse receipt.
     *
     * <p>This method updates the inventory with the user ID and date from the warehouse receipt,
     * retrieves the last inserted inventory ID, and updates the quantity and inventory ID for each
     * pricing in the warehouse receipt.
     *
     * @param warehouseReceipt The warehouse receipt containing the pricing list and other details.
     * @author ThanhTT
     */
    void updatePricingQuantity(WarehouseReceiptDTO warehouseReceipt);

    boolean isPricingCodeUnique(String pricingCode);
    Pricing findByPricingCode(String pricingCode);
    Page<Pricing> searchPricingsByProductAndCriteria( Long productId, String search, Pageable pageable);
    void updatePricing(Long pricingId, Pricing pricing);

    Pricing findByPricingId(Long pricingId);
    void deletePricing(Long pricingId);

    void deleteAllByProduct_ProductId(Long productId);
    void addPricings(Long productId, List<Pricing> pricings);

    void updateQuantity(Long pricingId, int quantity);
}
