package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a news entity.
 * Contains information about the news such as title, description, content, user, image URL, and creation date.
 * <p>Author: [QuyLV]</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    @Column(name = "title")
    @NotBlank(message = "Tiêu đề không được để trống !")
    @Size(max = 255, message = "Tối đa 255 ký tự !" )
    private String title;

    @Column(name = "news_description")
    @NotBlank(message = "Mô tả không được để trống !")
    @Size(max = 255, message = "Tối đa 255 ký tự !" )
    private String newsDescription;

    @Column(name = "content", columnDefinition = "TEXT")
    @NotBlank(message = "Nội dung không được để trống !")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "news_img_url")
    @Size(max = 255, message = "Tối đa 255 ký tự !" )
    @NotBlank(message = "Ảnh không được để trống !")
    private String newsImgUrl;

    @Column(name = "date_create")
    private LocalDate dateCreate;
}
