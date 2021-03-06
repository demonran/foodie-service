package com.foodie.portal.webmanage.model;

import com.foodie.portal.activity.model.ActivityStatus;
import com.foodie.portal.activity.model.ActivityType;
import lombok.Data;

@Data
public class ActivityRecommend {
    private String id;
    private String title;
    private String subTitle;
    private String description;
    private String cityId;
    private String images;
    private ActivityStatus status;
    private ActivityType type;
    private String merchantName;
    private boolean interestedRecommend;
    private boolean topRecommend;
}
