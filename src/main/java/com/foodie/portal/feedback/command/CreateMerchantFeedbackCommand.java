package com.foodie.portal.feedback.command;

import lombok.Data;

@Data
public class CreateMerchantFeedbackCommand {
    private String title;
    private String content;
}
