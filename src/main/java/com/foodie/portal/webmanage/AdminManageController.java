package com.foodie.portal.webmanage;

import com.foodie.portal.webmanage.command.AddBannerCommand;
import com.foodie.portal.webmanage.model.ActivityRecommend;
import com.foodie.portal.webmanage.model.ArticleRecommend;
import com.foodie.portal.webmanage.model.Banner;
import com.foodie.portal.webmanage.model.RestaurantRecommend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "（管理员）网站管理")
@RestController
@RequestMapping("admin/manage")
public class AdminManageController {


    @Autowired
    private WebManageApplicationService webManageApplicationService;

    @ApiOperation("添加banner")
    @PostMapping("banner")
    public Banner addBanner(@RequestBody AddBannerCommand bannerCommand) {
        return webManageApplicationService.addBanner(bannerCommand);
    }

    @ApiOperation("查询所有banner")
    @GetMapping("banner")
    public List<Banner> listBanners() {
        return webManageApplicationService.listBanners();
    }

    @ApiOperation("删除banner")
    @DeleteMapping("banner")
    public String removeBanner(String bannerId) {
        webManageApplicationService.removeBanner(bannerId);
        return bannerId;
    }
    @ApiOperation("修改banner")
    @PostMapping("banner/{id}")
    public void updateBanner(@PathVariable String id,@RequestBody AddBannerCommand bannerCommand) {
        webManageApplicationService.updateBanner(id, bannerCommand);
    }

    @ApiOperation("查询banner")
    @GetMapping("banner/{id}")
    public Banner detailBanner(@PathVariable String id) {
        return webManageApplicationService.detailBanner(id);
    }

    @ApiOperation("添加推荐活动")
    @PostMapping("activity-recommend")
    public void addRecommendActivity(String activityId) {
        webManageApplicationService.addRecommendActivity(activityId);
    }

    @ApiOperation("删除推荐活动")
    @DeleteMapping("activity-recommend")
    public void deleteRecommendActivity(String activityId) {
        webManageApplicationService.deleteRecommendActivity(activityId);
    }

    @ApiOperation("获取推荐活动列表")
    @GetMapping("activities-recommend")
    public List<ActivityRecommend> listRecommendActivities(String cityId) {
        return webManageApplicationService.listInterested(cityId);
    }

    @ApiOperation("TOP活动列表")
    @GetMapping("top-activities")
    public List<ActivityRecommend> listTopActivities() {
        return webManageApplicationService.listTopActivities();
    }

    @ApiOperation("添加TOP活动推荐")
    @PostMapping("top-activities")
    public void addTopActivities(String activityId) {
        webManageApplicationService.addTopActivity(activityId);
    }

    @ApiOperation("删除TOP活动推荐")
    @DeleteMapping("top-activities")
    public void deleteTopActivities(String activityId) {
        webManageApplicationService.deleteTopActivity(activityId);
    }

    @ApiOperation("推荐美食指南列表")
    @GetMapping("food-guide-recommend")
    public List<ArticleRecommend> listRecommendFoodGuides(String cityId) {
        return webManageApplicationService.listFoodGuides(cityId);
    }

    @ApiOperation("添加美食指南推荐")
    @PostMapping("food-guide-recommend")
    public void addRecommendArticle(String articleId) {
        webManageApplicationService.addRecommendFoodGuide(articleId);
    }

    @ApiOperation("删除美食指南推荐")
    @DeleteMapping("food-guide-recommend")
    public void deleteRecommendArticle(String articleId) {
        webManageApplicationService.deleteRecommendFoodGuide(articleId);
    }

    @ApiOperation("添加推荐餐厅")
    @PostMapping("restaurant-recommend")
    public void addRecommendRestaurant(String restaurantId) {
        webManageApplicationService.addRecommendRestaurant(restaurantId);
    }

    @ApiOperation("删除推荐餐厅")
    @DeleteMapping("restaurant-recommend")
    public void deleteRecommendRestaurant(String restaurantId) {
        webManageApplicationService.deleteRecommendRestaurant(restaurantId);
    }

    @ApiOperation("获取推荐餐厅列表")
    @GetMapping("restaurants-recommend")
    public List<RestaurantRecommend> listRecommendRestaurants(String cityId) {
        return webManageApplicationService.listInterestedRestaurant(cityId);
    }

}
