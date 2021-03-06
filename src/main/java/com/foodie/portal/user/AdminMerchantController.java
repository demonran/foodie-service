package com.foodie.portal.user;

import com.foodie.portal.commons.PageCommand;
import com.foodie.portal.commons.Pagination;
import com.foodie.portal.user.command.CreateMerchantCommand;
import com.foodie.portal.user.command.PassMerchantApplyCommand;
import com.foodie.portal.user.model.Merchant;
import com.foodie.portal.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "（管理员）用户/商家管理")
@RestController
@RequestMapping("admin")
public class AdminMerchantController {

    @Autowired
    private MerchantApplicationService merchantApplicationService;
    @Autowired
    private UserApplicationService userApplicationService;

    @ApiOperation("商家列表")
    @GetMapping("merchants")
    public Pagination<Merchant> merchants(PageCommand pageCommand) {
        return merchantApplicationService.merchants(pageCommand.getPage(), pageCommand.getSize());
    }

    @ApiOperation("所有商家列表")
    @GetMapping("merchants/all")
    public List<Merchant> merchants() {
        return merchantApplicationService.merchants();
    }

    @ApiOperation("添加商家")
    @PostMapping("merchants")
    public void addMerchant(@RequestBody CreateMerchantCommand merchantCommand) {
        merchantApplicationService.addMerchant(merchantCommand);
    }

    @ApiOperation("商家详情")
    @GetMapping("merchants/{id}")
    public Merchant detail(@PathVariable String id) {
        return merchantApplicationService.findById(id);
    }

    @ApiOperation("修改商家信息")
    @PatchMapping("merchants/{id}")
    public Merchant updateMerchant(@PathVariable String id, @RequestBody CreateMerchantCommand merchantCommand) {
        return merchantApplicationService.updateMerchant(id, merchantCommand);
    }

    @ApiOperation("删除商家")
    @DeleteMapping("merchants/{id}")
    public void deleteMerchant(@PathVariable String id) {
        merchantApplicationService.deleteMerchant(id);
    }

    @ApiOperation("商家审批通过")
    @PostMapping("merchants/{id}/pass")
    public void pass(@PathVariable String id, @RequestBody @Validated PassMerchantApplyCommand command) {
        merchantApplicationService.pass(id, command.getExtractRatio());
    }

    @ApiOperation("商家审批拒绝")
    @PostMapping("merchants/{id}/reject")
    public void reject(@PathVariable String id) {
        merchantApplicationService.reject(id);
    }

    @ApiOperation("待审核商家列表")
    @GetMapping("merchants/non-approval")
    public Pagination<Merchant> waitForApprovedMerchant(PageCommand pageCommand) {
        return merchantApplicationService.waitForApprovedMerchant(pageCommand.getPage(), pageCommand.getSize());
    }

    @ApiOperation("商家密码重置")
    @PostMapping("merchants/{id}/password-reset")
    public void resetMerchantPassword(@PathVariable String id) {
        merchantApplicationService.resetMerchantPassword(id);
    }

    @ApiOperation("用户列表")
    @GetMapping("user")
    public Pagination<User> listUser(PageCommand pageCommand) {
        return userApplicationService.list(pageCommand.getPage(), pageCommand.getSize());
    }
}

