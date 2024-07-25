package com.codegym.fashionshop.repository.dashboard;


import com.codegym.fashionshop.dto.TotalBillDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Repository class for retrieving total bill count and growth.
 * This class provides methods to fetch information about the total number of bills
 * and their growth compared to the previous month.
 * <p>
 * Author: KhangDV
 */
@Repository
public class ITotalBillRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new ITotalBillRepository with the specified DataSource.
     * <p>
     * @param dataSource the DataSource to be used for database access
     */
    public ITotalBillRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total number of bills and their growth percentage compared to the previous month.
     * The SQL query calculates the total number of bills for the current month and the previous month,
     * and then calculates the growth percentage based on these values.
     * <p>
     * @return a TotalBillDTO object containing total bill count and growth percentage.
     */
    public TotalBillDTO getTotalBillAndGrowth(){
        String sql = "SELECT total_bills, " +
                "((total_bills - total_bills_last_month) / total_bills_last_month) * 100 AS growth " +
                "FROM (" +
                "SELECT " +
                "(SELECT COUNT(DISTINCT bill_id) FROM bills " +
                "WHERE MONTH(date_create) = MONTH(CURDATE())) AS total_bills, " +
                "(SELECT COUNT(DISTINCT bill_id) FROM bills " +
                "WHERE MONTH(date_create) = MONTH(CURDATE()) - 1) AS total_bills_last_month) " +
                "AS bill_counts";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                TotalBillDTO dto = new TotalBillDTO();
                dto.setTotalBills(resultSet.getLong("total_bills"));
                dto.setGrowth(resultSet.getDouble("growth"));
                return dto;
            }
            return null;
        });
    }
}
