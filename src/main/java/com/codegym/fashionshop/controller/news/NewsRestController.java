package com.codegym.fashionshop.controller.news;

import com.codegym.fashionshop.dto.NewsDTO;
import com.codegym.fashionshop.dto.respone.ErrorDetail;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.entities.News;
import com.codegym.fashionshop.service.authenticate.IAppUserService;
import com.codegym.fashionshop.service.news.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for managing news-related operations.
 * Provides endpoints to retrieve and create news entries.
 * <p>Author: [QuyLV]</p>
 */
@RestController
@RequestMapping("/api")
public class NewsRestController {

    @Autowired
    private INewsService iNewsService;

    @Autowired
    private IAppUserService iAppUserService;

    /**
     * Retrieves a paginated list of all news entries.
     *
     * @param page the page number (default is 0 if not specified)
     * @return ResponseEntity containing a page of NewsDTO objects or NO_CONTENT if no news found
     */
    @GetMapping("/public/news")
    public ResponseEntity<Page<NewsDTO>> getAllNews(@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<NewsDTO> newsList = iNewsService.getAllNews(PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "news_id")));
        if (newsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }

    /**
     * Creates a new news entry.
     *
     * @param news           the news object to be created, validated using @Validated annotation
     * @param bindingResult  the result of the validation
     * @return ResponseEntity containing success message if creation is successful, BAD_REQUEST if validation errors occur
     */
    @PostMapping("/auth/news/create")
    public ResponseEntity<Object> createNews(@Validated @RequestBody News news, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = iAppUserService.findByUsername(username);

        news.setUser(user);
        news.setDateCreate(LocalDate.now());
        iNewsService.createNews(news);

        return new ResponseEntity<>("Thêm mới tin tức thành công !", HttpStatus.CREATED);
    }
    /**
     * Retrieves a specific news entry by its ID.
     *
     * @param newsId the ID of the news entry to retrieve
     * @return ResponseEntity containing the found NewsDTO object if successful, or NOT_FOUND status if not found
     */
    @GetMapping("/public/news/{newsId}")
    public ResponseEntity<NewsDTO> findNewsById(@PathVariable Long newsId) {
        NewsDTO newsDTO = iNewsService.findNewsByID(newsId);
        if (newsDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    /**
     * Deletes a news article by its ID.
     *
     * @param newsId The ID of the news article to delete
     * @return ResponseEntity with HttpStatus OK if deletion is successful, or HttpStatus NOT_FOUND if the news article is not found
     */
    @DeleteMapping("auth/news/{newsId}")
    public ResponseEntity<?> deleteNewsById(@PathVariable Long newsId) {
        if (!iNewsService.existsById(newsId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iNewsService.deleteById(newsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
