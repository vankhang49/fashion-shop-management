package com.codegym.fashionshop.repository.promotion;

import com.codegym.fashionshop.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for managing promotions.
 * Includes a custom query for retrieving a promotion by its promotion code.
 * Author: HoaNTT
 */
@Repository
public interface IPromotionRepository extends JpaRepository<Promotion,Long> {
    /**
     * Retrieves a promotion by its promotion code.
     *
     * @param promotionCode the promotion code
     * @return the promotion entity matching the given code, or null if none found
     */
    @Query("SELECT p FROM Promotion p WHERE p.promotionCode = :promotionCode")
    Promotion findByPromotionCode(@Param("promotionCode") String promotionCode);

    @Modifying
    @Transactional
    @Query("UPDATE Promotion p SET p.quantity = :quantity, p.enabled = :enabled WHERE p.promotionId = :promotionId")
    void updatePromotionQuantityAndEnabled(@Param("promotionId") Long promotionId, @Param("quantity") Integer quantity, @Param("enabled") Boolean enabled);
    Promotion findPromotionByPromotionCode(String promotionCode);

}
