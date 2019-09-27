package com.foodie.portal.webmanage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRecommendJpaRepository extends JpaRepository<ArticleRecommendEntity, String> {

    List<ArticleRecommendEntity> findByInterestedRecommend(boolean interestedRecommend);

}