package com.codegym.fashionshop.repository.bill;

import com.codegym.fashionshop.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing bills.
 * Provides methods for CRUD operations and custom queries related to bills.
 * Author: HoaNTT
 */
@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {
    /**

     * Checks if a bill with the given bill code exists.
     *
     * @param billCode the bill code to check
     * @return true if a bill with the given code exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Bill b WHERE b.billCode = :billCode")
    boolean existsByBillCode(@Param("billCode") String billCode);

    /**
     * Find bill entity by date create.
     *
     * @param dateCreate the date of creation
     * @Return the bill correct with creation date
     * @author KhangDV
     */
    @Query( value = "SELECT bill_id, bill_code, date_create, promotion_code, user_id, customer_id from bills where date_create = :dateCreate", nativeQuery = true)
    Bill findByDateCreate(@Param("dateCreate") LocalDate dateCreate);

    /**
     * Creates a new bill.
     *
     * @param billCode   the bill code
     * @param dateCreate the date of creation
     * @param customerId the ID of the customer associated with the bill
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO bills (bill_code, date_create, customer_id) VALUES (:billCode, :dateCreate, :customerId)", nativeQuery = true)
    void createBill(@Param("billCode") String billCode, @Param("dateCreate") LocalDate dateCreate, @Param("customerId") Long customerId);

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.accumulatedPoints = c.accumulatedPoints + :pointsToAdd WHERE c.customerId = :customerId")
    void updateAccumulatedPoints(@Param("customerId") Long customerId, @Param("pointsToAdd") int pointsToAdd);
    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.customerType.typeId = :typeId WHERE c.customerId = :customerId")
    void updateCustomerTypeOfCustomer(@Param("customerId") Long customerId, @Param("typeId") Long typeId);

    /**
     * Retrieves the daily sales revenue for a specific date.
     *
     * @param date The date for which to retrieve the daily sales revenue.
     * @return The daily sales revenue.
     * @author ThanhTT
     */
    @Query(value = "SELECT \n" +
            "    SUM(\n" +
            "        CASE \n" +
            "            WHEN p.discount IS NOT NULL THEN \n" +
            "                CASE \n" +
            "                    WHEN p.discount < 1 THEN (bi.quantity * pr.price) * (1 - p.discount) \n" +
            "                    ELSE (bi.quantity * pr.price) - p.discount \n" +
            "                END \n" +
            "            ELSE bi.quantity * pr.price \n" +
            "        END\n" +
            "    ) AS daily_revenue \n" +
            "FROM \n" +
            "    (\n" +
            "        SELECT \n" +
            "            b.bill_id, \n" +
            "            SUM(bi.quantity * pr.price) AS total_amount,\n" +
            "            b.date_create,\n" +
            "            b.promotion_code\n" +
            "        FROM bills b \n" +
            "        INNER JOIN bill_items bi ON b.bill_id = bi.bill_id \n" +
            "        INNER JOIN pricings pr ON bi.pricing_id = pr.pricing_id \n" +
            "        GROUP BY b.bill_id, b.date_create, b.promotion_code\n" +
            "    ) b\n" +
            "INNER JOIN bill_items bi ON b.bill_id = bi.bill_id \n" +
            "INNER JOIN pricings pr ON bi.pricing_id = pr.pricing_id \n" +
            "LEFT JOIN promotions p ON b.promotion_code = p.promotion_code \n" +
            "WHERE DATE(b.date_create) = :date", nativeQuery = true)
    Double getDailySalesRevenue(@Param("date") LocalDate date);

    /**
     * Retrieves the monthly sales revenue for a specific year and month.
     *
     * @param year  The year for which to retrieve the monthly sales revenue.
     * @param month The month for which to retrieve the monthly sales revenue.
     * @return The monthly sales revenue.
     * @author ThanhTT
     */
    @Query(value = "SELECT \n" +
            "    SUM(\n" +
            "        CASE \n" +
            "            WHEN p.discount IS NOT NULL THEN \n" +
            "                CASE \n" +
            "                    WHEN p.discount < 1 THEN (bi.quantity * pr.price) * (1 - p.discount) \n" +
            "                    ELSE (bi.quantity * pr.price) - p.discount \n" +
            "                END \n" +
            "            ELSE bi.quantity * pr.price \n" +
            "        END\n" +
            "    ) AS daily_revenue \n" +
            "FROM \n" +
            "    (\n" +
            "        SELECT \n" +
            "            b.bill_id, \n" +
            "            SUM(bi.quantity * pr.price) AS total_amount,\n" +
            "            b.date_create,\n" +
            "            b.promotion_code\n" +
            "        FROM bills b \n" +
            "        INNER JOIN bill_items bi ON b.bill_id = bi.bill_id \n" +
            "        INNER JOIN pricings pr ON bi.pricing_id = pr.pricing_id \n" +
            "        GROUP BY b.bill_id, b.date_create, b.promotion_code\n" +
            "    ) b\n" +
            "INNER JOIN bill_items bi ON b.bill_id = bi.bill_id \n" +
            "INNER JOIN pricings pr ON bi.pricing_id = pr.pricing_id \n" +
            "LEFT JOIN promotions p ON b.promotion_code = p.promotion_code \n" +
            "where year(b.date_create) = :year and month(b.date_create) = :month",
            nativeQuery = true)
    Double getMonthlySalesRevenue(@Param("year") int year, @Param("month") int month);

    /**
     * Retrieves the daily sales revenue for each day in a specific month.
     *
     * @param year  The year for which to retrieve the daily sales revenue.
     * @param month The month for which to retrieve the daily sales revenue.
     * @return A list of objects containing the day and the corresponding daily sales revenue.
     * @author ThanhTT
     */
    @Query(value = "SELECT \n" +
            "    DAY(b.date_create) AS day, \n" +
            "    SUM(\n" +
            "        CASE \n" +
            "            WHEN p.discount IS NOT NULL THEN \n" +
            "                CASE \n" +
            "                    WHEN p.discount < 1 THEN (bi.quantity * pr.price) * (1 - p.discount) \n" +
            "                    ELSE (bi.quantity * pr.price) - p.discount \n" +
            "                END \n" +
            "            ELSE bi.quantity * pr.price \n" +
            "        END\n" +
            "    ) AS daily_revenue\n" +
            "FROM (\n" +
            "    SELECT \n" +
            "        b.bill_id, \n" +
            "        b.date_create,\n" +
            "        b.promotion_code,\n" +
            "        SUM(bi.quantity * pr.price) AS total_amount\n" +
            "    FROM bills b \n" +
            "    INNER JOIN bill_items bi ON b.bill_id = bi.bill_id \n" +
            "    INNER JOIN pricings pr ON bi.pricing_id = pr.pricing_id \n" +
            "    GROUP BY b.bill_id\n" +
            ") b\n" +
            "INNER JOIN bill_items bi ON b.bill_id = bi.bill_id \n" +
            "INNER JOIN pricings pr ON bi.pricing_id = pr.pricing_id \n" +
            "LEFT JOIN promotions p ON b.promotion_code = p.promotion_code \n" +
            "WHERE YEAR(b.date_create) = :year \n" +
            "AND MONTH(b.date_create) = :month \n" +
            "GROUP BY DAY(b.date_create);",
            nativeQuery = true)
    List<Object[]> getDailySalesRevenueForMonth(@Param("year") int year, @Param("month") int month);


    List<Bill>findBillsByCustomer_CustomerId(Long customerId);

    /**
     * Fetches daily sold pricings for a given date.
     *
     * @param date the date for which to fetch sold pricings
     * @return a list of objects containing pricing code, pricing name, total quantity, and price
     * @author ThanhTT
     */
    @Query(value = "select p.pricing_code, p.pricing_name, sum(bi.quantity) as totalQuantity, p.price " +
            "from bills as b inner join bill_items as bi on b.bill_id = bi.bill_id\n" +
            "inner join pricings as p on bi.pricing_id = p.pricing_id " +
            "where date(b.date_create) = :date " +
            "group by p.pricing_code, p.pricing_name,\n" + " p.price;",
            nativeQuery = true)
    List<Object[]> getDailySoldPricings(@Param("date") LocalDate date);

    /**
     * Fetches monthly sold pricings for a given year and month.
     *
     * @param year  the year for which to fetch sold pricings
     * @param month the month for which to fetch sold pricings
     * @return a list of objects containing pricing code, pricing name, total quantity, and price
     * @author ThanhTT
     */
    @Query(value = "select p.pricing_code, p.pricing_name, sum(bi.quantity) as totalQuantity, p.price " +
            "from bills as b inner join bill_items as bi on b.bill_id = bi.bill_id\n" +
            "inner join pricings as p on bi.pricing_id = p.pricing_id " +
            "where year(b.date_create) = :year and month(b.date_create) = :month " +
            "group by p.pricing_code, p.pricing_name,\n" + " p.price;",
            nativeQuery = true)
    List<Object[]> getMonthlySoldPricings(@Param("year") int year, @Param("month") int month);

}
