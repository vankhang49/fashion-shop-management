package com.codegym.fashionshop.repository.dashboard;

import com.codegym.fashionshop.dto.TotalCustomerDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Repository class for retrieving total customer count and growth.
 * <p>
 * This class provides methods to fetch the total number of customers and the growth percentage
 * compared to the previous month.
 * </p>
 * Author: KhangDV
 */
@Repository
public class ITotalCustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new ITotalCustomerRepository with the specified DataSource.
     * <p>
     * @param dataSource the DataSource to be used for database access
     */
    public ITotalCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total number of customers for the current month and calculates the growth
     * percentage compared to the previous month.
     * <p>
     * The SQL query counts the number of distinct customer IDs from the bills table for both
     * the current month and the previous month. It then calculates the growth percentage
     * based on these values.
     * <p>
     * @return a TotalCustomerDTO object containing the total customer count and growth percentage.
     */
    public TotalCustomerDTO getTotalCustomerAndGrowth() {
         String sql = "SELECT total_customers, " +
                 "((total_customers - total_customers_last_month) / total_customers_last_month) * 100 AS growth " +
                 "FROM (" +
                 "SELECT " +
                 "(SELECT COUNT(DISTINCT customer_id) FROM bills " +
                 "WHERE MONTH(date_create) = MONTH(CURDATE())) AS total_customers, " +
                 "(SELECT COUNT(DISTINCT customer_id) FROM bills " +
                 "WHERE MONTH(date_create) = MONTH(CURDATE()) - 1) AS total_customers_last_month) " +
                 "AS customer_counts";
         return jdbcTemplate.query(sql, resultSet -> {
             if (resultSet.next()) {
                 TotalCustomerDTO dto = new TotalCustomerDTO();
                 dto.setTotalCustomers(resultSet.getLong("total_customers"));
                 dto.setGrowth(resultSet.getDouble("growth"));
                 return dto;
             }
             return null;
         });
    }
}
