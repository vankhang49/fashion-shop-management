package com.codegym.fashionshop.repository.dashboard;

import com.codegym.fashionshop.dto.BestSalespersonDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Repository class for retrieving top salesperson data.
 * This class provides methods to fetch information about the best salespersons
 * based on their sales performance.
 * <p>
 * Author: KhangDV
 */
@Repository
public class BestSalespersonRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new BestSalespersonRepository with the specified DataSource.
     * <p>
     * @param dataSource the DataSource to be used for database access
     */
    public BestSalespersonRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves a list of top salespersons based on their sales revenue and quantity sold
     * for the current month. The results are limited to the top 5 best salespersons.
     * <p>
     * The SQL query joins the bills, app_user, bill_items, and pricings tables
     * to calculate the total revenue and quantity sold by each user for the current month.
     * The results are then grouped by the user's full name and ordered by revenue in descending order.
     * <p>
     * @return a list of BestSalespersonDTO objects containing top salesperson information.
     */
    public List<BestSalespersonDTO> getBestSalespersons() {
        String sql = "select u.full_name, sum(p.price*bi.quantity) as revenues, sum(bi.quantity) as quantity from bills b " +
                "join app_user u on b.user_id = u.user_id " +
                "join bill_items bi on b.bill_id = bi.bill_id " +
                "join pricings p on bi.pricing_id = p.pricing_id " +
                "where MONTH(b.date_create) = MONTH(CURDATE()) " +
                "group by u.full_name order by revenues desc limit 5";

        RowMapper<BestSalespersonDTO> rowMapper = (resultSet, rowNum) -> {
            BestSalespersonDTO dto = new BestSalespersonDTO();
            dto.setFullName(resultSet.getString("full_name"));
            dto.setRevenue(resultSet.getDouble("revenues"));
            dto.setQuantity(resultSet.getInt("quantity"));
            return dto;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }
}
