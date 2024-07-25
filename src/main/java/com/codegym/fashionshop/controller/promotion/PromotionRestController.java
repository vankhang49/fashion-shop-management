package com.codegym.fashionshop.controller.promotion;

import com.codegym.fashionshop.entities.Promotion;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.promotion.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/promotions")
@CrossOrigin("*")
public class PromotionRestController {

    @Autowired
    private IPromotionService promotionService;

    /**
     * Retrieve promotion details based on the promotion code.
     *
     * @param promotionCode the code of the promotion to look up
     * @return ResponseEntity containing the Promotion object if found, otherwise throws a NotFoundException
     * @throws HttpExceptions.NotFoundException if the promotion information is not found
     * @author User
     */
    @GetMapping
    public ResponseEntity<Promotion> getPromotionByCode(@RequestParam(name = "promotionCode") String promotionCode) {
        Promotion promotion = promotionService.findByPromotionCode(promotionCode);
        if (promotion == null) {
            throw new HttpExceptions.NotFoundException("Promotion information not found");
        }
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    /**
     * Use the promotion based on the promotion code.
     *
     * @param promotionCode the code of the promotion to be used
     * @return ResponseEntity containing the Promotion object if successful, otherwise throws a NotFoundException
     * @throws HttpExceptions.NotFoundException if the promotion information is not found or the promotion is not available
     * @author User
     */
    @PostMapping("/use")
    public ResponseEntity<Promotion> usePromotion(@RequestParam(name = "promotionCode") String promotionCode) {
        Promotion promotion = promotionService.usePromotion(promotionCode);
        if (promotion == null) {
            throw new HttpExceptions.NotFoundException("Promotion information not found or promotion is not available");
        }
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }
}

