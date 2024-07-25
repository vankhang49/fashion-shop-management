package com.codegym.fashionshop.service.news.impl;

import com.codegym.fashionshop.dto.NewsDTO;
import com.codegym.fashionshop.entities.News;
import com.codegym.fashionshop.repository.news.INewsRepository;
import com.codegym.fashionshop.service.news.INewsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service implementation for managing news-related operations.
 * <p>Author: [QuyLV]</p>
 */
@Service
public class NewsService implements INewsService {

    @Autowired
    private INewsRepository iNewsRepository;

    /**
     * Creates a new news entry.
     *
     * @param news the news object to be created
     */
    @Transactional
    @Override
    public void createNews(News news) {
        iNewsRepository.createNews(
                news.getTitle(),
                news.getNewsDescription(),
                news.getContent(),
                news.getUser().getUserId(),
                news.getNewsImgUrl(),
                LocalDate.now()
        );
    }

    /**
     * Retrieves a paginated list of all news entries as NewsDTO objects.
     *
     * @param pageable the pagination information
     * @return a page of NewsDTO objects
     */
    @Override
    public Page<NewsDTO> getAllNews(Pageable pageable) {
        Page<Object[]> results = iNewsRepository.findAllNews(pageable);
        return results.map(this::convertToNewsDTO);
    }

    /**
     * Converts an Object array retrieved from the repository into a NewsDTO object.
     *
     * @param row the Object array representing a news entry
     * @return the NewsDTO object created from the Object array
     */
    private NewsDTO convertToNewsDTO(Object[] row) {
        NewsDTO dto = new NewsDTO();
        dto.setNewsId((Long) row[0]);
        dto.setTitle((String) row[1]);
        dto.setNewsDescription((String) row[2]);
        dto.setContent((String) row[3]);
        dto.setNewsImgUrl((String) row[4]);
        dto.setDateCreate(((java.sql.Date) row[5]).toLocalDate());
        dto.setFullName((String) row[6]);
        return dto;
    }

    /**
     * Retrieves a NewsDTO object by its ID.
     *
     * @param newsId the ID of the news to retrieve
     * @return the NewsDTO object corresponding to the given ID, or null if not found
     */

    @Override
    public NewsDTO findNewsByID(Long newsId) {
        Object result = iNewsRepository.findNewsById(newsId);
        if (result != null) {
            NewsDTO dto = new NewsDTO();
            dto.setNewsId((Long) ((Object[]) result)[0]);
            dto.setTitle((String) ((Object[]) result)[1]);
            dto.setNewsDescription((String) ((Object[]) result)[2]);
            dto.setContent((String) ((Object[]) result)[3]);
            dto.setNewsImgUrl((String) ((Object[]) result)[4]);
            dto.setDateCreate(((java.sql.Date) ((Object[]) result)[5]).toLocalDate());
            dto.setFullName((String) ((Object[]) result)[6]);
            return dto;
        }
        return null;
    }

    @Override
    public boolean existsById(Long newsId) {
        return iNewsRepository.existsById(newsId);
    }

    @Override
    public void deleteById(Long newsId) {
        iNewsRepository.deleteNewsById(newsId);
    }

}
