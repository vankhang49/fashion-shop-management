package com.codegym.fashionshop.repository.news;

import com.codegym.fashionshop.entities.News;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Repository interface for managing {@link News} entities.
 * Provides methods for retrieving and creating news entries in the database.
 * <p>Author: [QuyLV]</p>
 */
public interface INewsRepository extends JpaRepository<News, Long> {

    /**
     * Retrieves a paginated list of all news with associated user full names.
     *
     * @param pageable the pagination information
     * @return a page of news entries with associated user full names
     */
    @Query(value = "SELECT n.news_id, n.title, n.news_description, n.content, n.news_img_url, n.date_create, u.full_name " +
            "FROM news n " +
            "LEFT JOIN app_user u ON n.user_id = u.user_id ",
            countQuery = "SELECT COUNT(*) FROM news",
            nativeQuery = true)
    Page<Object[]> findAllNews(Pageable pageable);

    /**
     * Creates a new news entry in the database.
     *
     * @param title the title of the news
     * @param newsDescription the description of the news
     * @param content the content of the news
     * @param userId the ID of the user who created the news
     * @param newsImgUrl the URL of the news image
     * @param dateCreate the date the news was created
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO news (title, news_description, content, user_id, news_img_url, date_create) " +
            "VALUES (:title, :newsDescription, :content, :userId, :newsImgUrl, :dateCreate)", nativeQuery = true)
    void createNews(@Param("title") String title,
                    @Param("newsDescription") String newsDescription,
                    @Param("content") String content,
                    @Param("userId") Long userId,
                    @Param("newsImgUrl") String newsImgUrl,
                    @Param("dateCreate") LocalDate dateCreate);

    /**
     * Retrieves detailed information about a news entry by its ID.
     *
     * @param newsId the ID of the news entry to retrieve
     * @return an Object array containing the details of the news entry:
     *         [newsId, title, newsDescription, content, newsImgUrl, dateCreate, fullName]
     */
    @Query(value = "SELECT n.news_id, n.title, n.news_description, n.content, n.news_img_url, n.date_create, u.full_name " +
            "FROM news n " +
            "LEFT JOIN app_user u ON n.user_id = u.user_id " +
            "WHERE n.news_id = :newsId",
            nativeQuery = true)
    Object findNewsById(@Param("newsId") Long newsId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM news WHERE news_id = :newsId", nativeQuery = true)
    void deleteNewsById(Long newsId);
}
