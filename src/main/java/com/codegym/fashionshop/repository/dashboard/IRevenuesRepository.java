package com.codegym.fashionshop.repository.dashboard;

import com.codegym.fashionshop.dto.RevenueDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Repository class for retrieving total revenue and growth data.
 * This class provides methods to fetch total revenue and growth percentage
 * based on different time periods (week, month, year).
 * <p>
 * Author: KhangDV
 */
@Repository
public class IRevenuesRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new IRevenuesRepository with the specified DataSource.
     * <p>
     * @param dataSource the DataSource to be used for database access
     */
    public IRevenuesRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves total revenue and growth percentage based on the selected time option.
     * <p>
     * Executes SQL query to retrieve total revenues and growth percentage for the specified time period.
     * The SQL query dynamically selects total revenue and calculates growth percentage
     * for the current period and the previous period based on the selected option (week, month, year).
     * <p>
     * @param option 0 for week, 1 for month, 2 for year
     * @return a RevenueDTO object containing total revenue and growth percentage
     * @throws IllegalArgumentException if the option is invalid
     */
    public RevenueDTO getTotalRevenueAndGrowth(int option) {
        String dateCondition;
        String curDateCondition = switch (option) {
            case 0 -> {
                dateCondition = "YEARWEEK(b.date_create, 1)";
                yield "YEARWEEK(CURDATE(), 1)";
            }
            case 1 -> {
                dateCondition = "MONTH(b.date_create)";
                yield "MONTH(CURDATE())";
            }
            case 2 -> {
                dateCondition = "YEAR(b.date_create)";
                yield "YEAR(CURDATE())";
            }
            default -> throw new IllegalArgumentException("Invalid option: " + option);
        };
        String sql = "SELECT total_revenues, " +
                "((total_revenues - total_revenues_last_period) / total_revenues_last_period) * 100 AS growth " +
                "FROM ( " +
                "SELECT " +
                "(SELECT sum(p.price * bi.quantity) AS total_revenues FROM bills b " +
                "JOIN bill_items bi ON b.bill_id = bi.bill_id " +
                "JOIN pricings p ON bi.pricing_id = p.pricing_id " +
                "WHERE " + dateCondition + " = " + curDateCondition + ") AS total_revenues, " +
                "(SELECT sum(p.price * bi.quantity) AS total_revenues FROM bills b " +
                "JOIN bill_items bi ON b.bill_id = bi.bill_id " +
                "JOIN pricings p ON bi.pricing_id = p.pricing_id " +
                "WHERE " + dateCondition + " = " + curDateCondition + "- 1) AS total_revenues_last_period) " +
                "AS revenue_counts";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                RevenueDTO dto = new RevenueDTO();
                dto.setTotalRevenue(resultSet.getDouble("total_revenues"));
                dto.setGrowth(resultSet.getDouble("growth"));
                return dto;
            }
            return null;
        });
    }
}
