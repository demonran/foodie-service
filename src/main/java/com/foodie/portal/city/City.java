package com.foodie.portal.city;

import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class City {

    private String id;
    private String name;
    private String description;
    private String introduction;
    private String images;
    private boolean showOnActivity;
    private boolean showOnRestaurant;

    public City() {
        this.id = IdUtil.fastSimpleUUID();
    }


    public City(String name, String desc, String introduction, String images, boolean showOnActivity, boolean showOnRestaurant) {
        this();
        this.name = name;
        this.introduction = introduction;
        this.description = desc;
        this.images = images;
        this.showOnActivity = showOnActivity;
        this.showOnRestaurant = showOnRestaurant;
    }

    public static City create(String name, String desc, String introduction, String images, boolean showOnActivity, boolean showOnRestaurant) {
        return new City(name, desc, introduction, images, showOnActivity, showOnRestaurant);
    }

    public void update(String name, String desc, String introduction, String images, boolean showOnActivity, boolean showOnRestaurant) {
        this.name = name;
        this.introduction = introduction;
        this.description = desc;
        this.images = images;
        this.showOnActivity = showOnActivity;
        this.showOnRestaurant = showOnRestaurant;
    }
}
