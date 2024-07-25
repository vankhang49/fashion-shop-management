package com.codegym.fashionshop.service.promotion;

import com.codegym.fashionshop.entities.Promotion;

import java.util.List;

public interface IPromotionService {
    Promotion findByPromotionCode(String promotionCode);
    Promotion usePromotion(String promotionCode);
}
