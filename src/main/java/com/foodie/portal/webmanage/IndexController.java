package com.foodie.portal.webmanage;

import com.foodie.portal.activity.model.Activity;
import com.foodie.portal.article.Article;
import com.foodie.portal.article.ArticleType;
import com.foodie.portal.commons.PageCommand;
import com.foodie.portal.commons.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private WebManageApplicationService webManageApplicationService;

    @GetMapping("featured-areas")
    public FeaturedAreasDto featuredAreas(String cityId) {
        return webManageApplicationService.featuredAreas(cityId);
    }

    @GetMapping("city-activities")
    public List<Activity> retrieveCityActivity(String cityId) {
        return webManageApplicationService.fetchRecommendCityActivities(cityId);
    }

    @GetMapping("food-guide")
    public Pagination<Article> foodGuide(String cityId, ArticleType type, PageCommand pageCommand) {
        return webManageApplicationService.foodGuide(cityId, type, pageCommand.getPage(), pageCommand.getSize());
    }

    @GetMapping("food-guide")
    public Pagination<Article> recommendActivities(String cityId, ArticleType type, PageCommand pageCommand) {
        return webManageApplicationService.foodGuide(cityId, type, pageCommand.getPage(), pageCommand.getSize());
    }

}
