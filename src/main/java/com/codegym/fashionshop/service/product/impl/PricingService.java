package com.codegym.fashionshop.service.product.impl;

import com.codegym.fashionshop.dto.WarehouseReceiptDTO;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.repository.product.IInventoryRepository;
import com.codegym.fashionshop.repository.product.IPricingRepository;
import com.codegym.fashionshop.repository.product.IProductRepository;
import com.codegym.fashionshop.service.authenticate.IAppUserService;
import com.codegym.fashionshop.service.product.IPricingService;
import com.codegym.fashionshop.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PricingService implements IPricingService {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IInventoryRepository inventoryRepository;
    @Autowired
    private IPricingRepository pricingRepository;
    @Autowired
    private IProductRepository productRepository;
    @Override
    public List<Pricing> findAllPricing() {
        return pricingRepository.findAllPricings();
    }

    @Override
    public Page<Pricing> findAllPricing(Pageable pageable) {
        return pricingRepository.findAll(pageable);
    }

    @Override
    public Page<Pricing> findAllByProduct_ProductId(Long productId,Pageable pageable) {
        return pricingRepository.findAllByProduct_ProductId(productId,pageable);
    }

//    @Override
//    public void createPricing(Pricing pricing) {
//        pricingRepository.save(pricing);
//    }

    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public void updatePricingQuantity(WarehouseReceiptDTO warehouseReceipt) {
        AppUser user = appUserService.findByUsername(warehouseReceipt.getUsername());
        inventoryRepository.saveInventory(user.getUserId(), warehouseReceipt.getDate(), warehouseReceipt.getReceiptId());
        Long inventoryId = inventoryRepository.getLastInsertId();
        System.out.println(inventoryId);
        List<Pricing> updatedPricing = warehouseReceipt.getPricingList();
        for (Pricing p : updatedPricing) {
            pricingRepository.updateQuantityAndInventory(p.getPricingId(), p.getQuantity(), inventoryId);
        }
    }


    public boolean isPricingCodeUnique(String pricingCode) {
        return !pricingRepository.existsByPricingCode(pricingCode);
    }

    @Override
    public Pricing findByPricingCode(String pricingCode) {
        return pricingRepository.findByPricingCode(pricingCode);
    }

    @Override
    public Page<Pricing> searchPricingsByProductAndCriteria( Long productId, String search, Pageable pageable) {
        return pricingRepository.searchByProductAndCriteria(productId,search,pageable);
    }

    @Override
    public void updatePricing(Long pricingId, Pricing pricing) {
        pricingRepository.updatePricing(pricingId, pricing.getPricingName(), pricing.getPricingCode(), pricing.getPrice(), pricing.getSize(), pricing.getQrCode(), pricing.getQuantity(), pricing.getColor(), pricing.getPricingImgUrl(), pricing.getInventory());
    }

    @Override
    public Pricing findByPricingId(Long pricingId) {
        return pricingRepository.findPricingByPricingId(pricingId);
    }

    @Override
    public void deletePricing(Long pricingId) {
        pricingRepository.deleteById(pricingId);
    }

    @Override
    public void deleteAllByProduct_ProductId(Long productId) {
        pricingRepository.deleteAllByProduct_ProductId(productId);
    }

    @Override
    public void createPricing(Pricing pricing) {
        pricingRepository.createPricing(
                pricing.getPricingName(),
                pricing.getPricingCode(),
                pricing.getProduct().getProductId(),
                pricing.getPrice(),
                pricing.getSize(),
                pricing.getQrCode(),
                Long.valueOf(pricing.getQuantity()),
                pricing.getInventory().getInventoryId(),
                pricing.getColor().getColorId(),
                pricing.getPricingImgUrl()
                 // assuming this is the inventory_id field in Pricing entity
        );
    }
    @Override
    @Transactional
    public void addPricings(Long productId, List<Pricing> pricings) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        for (Pricing pricing : pricings) {
            pricing.setProduct(product);
            pricingRepository.save(pricing);
        }
    }
    public void updateQuantity(Long pricingId, int quantity) {
        Pricing pricing = pricingRepository.findById(pricingId)
                .orElseThrow(() -> new RuntimeException("Pricing not found"));
        int updatedQuantity = pricing.getQuantity() - quantity;
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be less than zero");
        }
        pricing.setQuantity(updatedQuantity);
        pricingRepository.save(pricing);
    }

  }



