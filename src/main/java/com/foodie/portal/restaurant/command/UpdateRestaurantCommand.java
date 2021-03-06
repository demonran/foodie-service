package com.foodie.portal.restaurant.command;

import com.foodie.portal.restaurant.model.RestaurantType;
import com.foodie.portal.restaurant.model.SetMeal;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateRestaurantCommand {
    private String title;
    private String subTitle;
    private String images;
    private String content;
    private String cityId;
    private String area;
    private String address;
    private List<SetMeal> setMeals;
    @NotNull
    private RestaurantType type;
}
