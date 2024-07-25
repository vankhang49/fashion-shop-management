package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for representing news entries.
 * <p>Author: [QuyLV]</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    private Long newsId;
    private String title;
    private String newsDescription;
    private String content;
    private String fullName;
    private String newsImgUrl;
    private LocalDate dateCreate;
}
