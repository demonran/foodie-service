package com.foodie.portal.feedback;

import com.foodie.portal.feedback.command.CreateMerchantFeedbackCommand;
import com.foodie.portal.user.model.Merchant;
import io.swagger.annotations.Api;
import lombok.var;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "（商家）反馈")
@RestController
@RequestMapping("merchant/feedback")
public class MerchantFeedbackController {

    @Autowired
    private MerchantFeedbackApplicationService feedbackApplicationService;

    @PostMapping
    public void create(@RequestBody CreateMerchantFeedbackCommand command) {
        var merchant = (Merchant) SecurityUtils.getSubject().getPrincipal();
        feedbackApplicationService.create(command, merchant);
    }
}
