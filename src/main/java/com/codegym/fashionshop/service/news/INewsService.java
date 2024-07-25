package com.codegym.fashionshop.service.news;

import com.codegym.fashionshop.dto.NewsDTO;
import com.codegym.fashionshop.entities.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing news-related operations.
 * <p>Author: [QuyLV]</p>
 */
public interface INewsService {

    /**
     * Creates a new news entry.
     *
     * @param news the news object to be created
     */
    void createNews(News news);

    /**
     * Retrieves a paginated list of all news entries.
     *
     * @param pageable the pagination information
     * @return a page of NewsDTO objects
     */
    Page<NewsDTO> getAllNews(Pageable pageable);

    /**
     * Retrieves a specific news entry by its ID.
     *
     * @param newsId the ID of the news entry to retrieve
     * @return the NewsDTO object corresponding to the given ID, or null if not found
     */
    NewsDTO findNewsByID(Long newsId);

    boolean existsById(Long newsId);

    void deleteById(Long newsId);
}
