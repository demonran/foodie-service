package com.foodie.portal.web.controller;

import com.foodie.portal.user.MerchantApplicationService;
import com.foodie.portal.web.command.ApplyMerchantCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户页面接口")
@RestController
public class MerchantApplyController {

    @Autowired
    private MerchantApplicationService merchantApplicationService;

    @ApiOperation("商家申请")
    @PostMapping("merchant-apply")
    public void applyMerchant(@RequestBody ApplyMerchantCommand command) {
        merchantApplicationService.applyMerchant(command);
    }
}
