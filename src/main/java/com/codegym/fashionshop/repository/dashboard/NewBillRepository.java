package com.codegym.fashionshop.repository.dashboard;

import com.codegym.fashionshop.dto.NewBillDTO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Repository class for retrieving new bill information.
 * <p>
 * This class interacts with the database to fetch details of the newest bills,
 * including customer names and creation dates, ordered by the most recent dates.
 * </p>
 *
 * author KhangDV
 */
@Repository
public class NewBillRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor to initialize the repository with a data source.
     * <p>
     * @param dataSource The JDBC DataSource to be used for database interaction.
     */
    public NewBillRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves a list of the newest bills along with their customer names,
     * ordered by their creation date in descending order. The results are limited
     * to the top 5 newest bills.
     * <p>
     * Executes SQL query to retrieve information about the latest bills
     * created in the system, including customer name and creation date.
     * The results are ordered by creation date in descending order and limited to 5.
     * <p>
     * @return A list of NewBillDTO objects containing information about the newest bills.
     */
    public List<NewBillDTO> getNewBillDTOList() {
        String sql = "select c.customer_name, b.date_create from bills b " +
                "join customers c on b.customer_id = c.customer_id order by b.date_create desc limit 5";
        RowMapper<NewBillDTO> rowMapper = (resultSet, rowNum) -> {
            NewBillDTO dto = new NewBillDTO();
            dto.setCustomerName(resultSet.getString("customer_name"));
            dto.setDateCreate(resultSet.getDate("date_create").toLocalDate());
            return dto;
        };
        return jdbcTemplate.query(sql, rowMapper);
    }
}
