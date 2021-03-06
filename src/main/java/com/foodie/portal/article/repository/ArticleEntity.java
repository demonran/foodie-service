package com.foodie.portal.article.repository;

import com.foodie.portal.article.ArticleType;
import com.foodie.portal.city.City;
import com.foodie.portal.city.repository.CityEntity;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "foodie_article")
public class ArticleEntity {
    @Id
    private String id;
    private String title;
    private String subTitle;
    private String cover;
    private String content;
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private CityEntity city;
    @Enumerated(EnumType.STRING)
    private ArticleType type;
    private Instant createdAt;
}
