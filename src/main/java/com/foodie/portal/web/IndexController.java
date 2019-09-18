package com.foodie.portal.web;

import com.foodie.portal.activity.model.Activity;
import com.foodie.portal.article.Article;
import com.foodie.portal.city.representation.CitySummaryRepresentation;
import com.foodie.portal.web.representation.IndexRepresentationService;
import com.foodie.portal.webmanage.Banner;
import com.foodie.portal.webmanage.FeaturedAreasDto;
import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.MockConfig;
import com.github.jsonzou.jmockdata.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户首页接口")
@RestController
public class IndexController {

    @Autowired
    private IndexRepresentationService indexRepresentationService;

    @ApiOperation("首页banner列表")
    @GetMapping("banners")
    public List<Banner> banners() {
        MockConfig config = MockConfig.newInstance()
                .subConfig("url")
                .stringRegex("http://www\\.baidu\\.com/test\\.png")
                .subConfig("link")
                .stringRegex("http://www\\.baidu\\.com")
                .globalConfig();
        return JMockData.mock(new TypeReference<List<Banner>>() {
        }, config);
    }

    @ApiOperation("首页推荐城市活动")
    @GetMapping("featured-activity")
    public List<FeaturedAreasDto> featuredCityActivity() {
        return indexRepresentationService.featuredActivity();
    }


    @ApiOperation("首页推荐美食指南")
    @GetMapping("top-rated-food-guide")
    public List<Article> recommendFoodGuide() {
        return indexRepresentationService.featuredCityFoodGuide();
    }

    @ApiOperation("首页推荐活动")
    @GetMapping("top-rated-activities")
    public List<Activity> topRatedActivities() {
        return indexRepresentationService.topRatedActivities();
    }

}
