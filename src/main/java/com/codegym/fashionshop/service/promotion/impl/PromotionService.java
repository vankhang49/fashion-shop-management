package com.codegym.fashionshop.service.promotion.impl;

import com.codegym.fashionshop.entities.Promotion;
import com.codegym.fashionshop.repository.promotion.IPromotionRepository;
import com.codegym.fashionshop.service.promotion.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PromotionService implements IPromotionService {
    @Autowired
    private IPromotionRepository promotionRepository;
    @Override
    public Promotion findByPromotionCode(String promotionCode) {
        return promotionRepository.findByPromotionCode(promotionCode);
    }
  
    @Override
    @Transactional
    public Promotion usePromotion(String promotionCode) {
        Promotion promotion = promotionRepository.findByPromotionCode(promotionCode);
        if (promotion != null && promotion.getEnabled()) {
            promotion.usePromotion();
            promotionRepository.updatePromotionQuantityAndEnabled(promotion.getPromotionId(), promotion.getQuantity(), promotion.getEnabled());
        }
        return promotion;
    }

}
