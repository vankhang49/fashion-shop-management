package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Repository interface for managing inventory operations.
 *
 * @author ThanhTT
 */
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    /**
     * Saves a new inventory record into the database.
     *
     * @param userId The ID of the user who created the inventory.
     * @param createdDate The date when the inventory was created.
     * @param code The ticket code associated with the inventory.
     * @author ThanhTT
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO inventories(date_create, ticket_code, user_id) VALUES (:createdDate, :code, :userId)", nativeQuery = true)
    void saveInventory(@Param("userId") Long userId, @Param("createdDate") LocalDate createdDate, @Param("code") String code);

    /**
     * Retrieves the ID of the last inserted inventory record.
     *
     * @return The ID of the last inserted inventory record.
     * @author ThanhTT
     */
    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertId();
}
