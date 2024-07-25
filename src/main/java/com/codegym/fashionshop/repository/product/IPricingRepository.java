package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Color;
import com.codegym.fashionshop.entities.Inventory;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface for managing pricings.
 * Includes CRUD operations and custom queries for pricings.
 * Author: HoaNTT
 */
@Repository
public interface IPricingRepository extends JpaRepository<Pricing, Long> {

    /**
     * Retrieves all pricings with product and color details.
     *
     * @return a list of pricings with associated product and color information
     */
    @Query(value = "SELECT p.*, pr.product_name, c.color_name FROM pricings p " +
            "JOIN products pr ON p.product_id = pr.product_id " +
            "JOIN colories c ON p.color_id = c.color_id", nativeQuery = true)
    List<Pricing> findAllPricings();

    /**
     * Creates a new pricing.
     *
     * @param pricingName    the pricing name
     * @param pricingCode    the pricing code
     * @param productId      the ID of the associated product
     * @param price          the pricing price
     * @param size           the sizing information
     * @param qrCode         the QR code
     * @param quantity       the quantity available
     * @param colorId        the ID of the associated color
     * @param pricingImgUrl  the URL of the pricing image
     * @param inventoryId    the ID of the associated inventory
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO pricings (pricing_name, pricing_code, product_id, price, size, qr_code, quantity, color_id, pricing_image, inventory_id) " +
            "VALUES (:pricingName, :pricingCode, :productId, :price, :size, :qrCode, :quantity, :colorId, :pricingImgUrl, :inventoryId)",
            nativeQuery = true)
    void createPricing(@Param("pricingName") String pricingName,
                       @Param("pricingCode") String pricingCode,
                       @Param("productId") Long productId,
                       @Param("price") Double price,
                       @Param("size") String size,
                       @Param("qrCode") String qrCode,
                       @Param("quantity") Long quantity,
                       @Param("inventoryId") Long inventoryId,
                       @Param("colorId") Integer colorId,
                       @Param("pricingImgUrl") String pricingImgUrl);

    /**
     * Updates the quantity and inventory ID of a pricing in the database.
     *
     * <p>This method updates the quantity of a specific pricing by adding the provided quantity
     * to the existing quantity. It also sets the inventory ID for the specified pricing.
     *
     * <p>The method is annotated with {@link Modifying} and {@link Transactional} to indicate that it
     * performs a modifying query and should be executed within a transaction context.
     *
     * @param id The ID of the pricing to update.
     * @param quantity The quantity to add to the existing quantity.
     * @param inventoryId The inventory ID to set for the pricing.
     * @author ThanhTT
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE pricings SET quantity = quantity + :quantity, inventory_id = :inventoryId WHERE pricing_id = :id", nativeQuery = true)
    void updateQuantityAndInventory(@Param("id") Long id, @Param("quantity") int quantity, @Param("inventoryId") Long inventoryId);

    /**
     * Retrieves all pricings with pagination.
     *
     * @param pageable pagination information
     * @return a page of pricings
     */
    @Query("SELECT p FROM Pricing p")
    Page<Pricing> findAll(Pageable pageable);

    /**
     * Retrieves all pricings associated with a specific product ID with pagination.
     *
     * @param productId the ID of the product
     * @param pageable  pagination information
     * @return a page of pricings associated with the specified product ID
     */
    @Query("SELECT p FROM Pricing p JOIN p.product prod WHERE prod.productId = :productId")
    Page<Pricing> findAllByProduct_ProductId(@Param("productId") Long productId, Pageable pageable);

    /**
     * Retrieves a pricing by its pricing code.
     *
     * @param pricingCode the pricing code
     * @return the pricing entity
     */
    @Query("SELECT p FROM Pricing p WHERE p.pricingCode = :pricingCode")
    Pricing findByPricingCode(@Param("pricingCode") String pricingCode);

    /**
     * Checks if a pricing with the given pricing code exists.
     *
     * @param pricingCode the pricing code to check
     * @return true if a pricing with the given code exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Pricing p WHERE p.pricingCode = :pricingCode")
    boolean existsByPricingCode(@Param("pricingCode") String pricingCode);

    @Query("SELECT p FROM Pricing p WHERE p.product.productId = :productId " +
            "AND p.enabled = true " +
            "AND (p.pricingCode LIKE %:search% " +
            "OR p.pricingName LIKE %:search% " +
            "OR p.size LIKE %:search% " +
            "OR p.color.colorName LIKE %:search%)")
    Page<Pricing> searchByProductAndCriteria(
            @Param("productId") Long productId,
            @Param("search") String search,
            Pageable pageable);
    @Transactional
    @Modifying
    @Query("UPDATE Pricing p " +
            "SET p.pricingName = :pricingName, " +
            "    p.pricingCode = :pricingCode, " +
            "    p.price = :price, " +
            "    p.size = :size, " +
            "    p.qrCode = :qrCode, " +
            "    p.quantity = :quantity, " +
            "    p.color = :color, " +
            "    p.pricingImgUrl = :pricingImgUrl, " +
            "    p.inventory = :inventory " +
            "WHERE p.pricingId = :pricingId")
    void updatePricing(Long pricingId, String pricingName, String pricingCode, Double price, String size, String qrCode, Integer quantity, Color color, String pricingImgUrl, Inventory inventory);

    Pricing findPricingByPricingId(Long pricingId);
    @Transactional
    @Modifying
    @Query("UPDATE Pricing p " +
            "SET p.enabled = :enabled " +
            "WHERE p.pricingId = :pricingId")
    void deletePricing(Long pricingId, Boolean enabled);
    @Transactional
    @Modifying
    @Query("DELETE FROM Pricing p WHERE p.product.productId = :productId")
    void deleteAllByProduct_ProductId(@Param("productId") Long productId);
}