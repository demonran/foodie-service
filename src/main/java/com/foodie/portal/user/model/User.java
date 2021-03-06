package com.foodie.portal.user.model;

import cn.hutool.core.util.IdUtil;
import com.foodie.portal.commons.ErrorCode;
import com.foodie.portal.commons.RestException;
import com.foodie.portal.commons.model.IdType;
import com.foodie.portal.commons.utils.EncryptUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String id;
    private String email;
    private String password;
    private String avatar;
    private String lastName;
    private String firstName;
    private String nationality;
    private IdType idType;
    private String idNumber;

    public User(String email, String decryptPassword) {
        this.id = IdUtil.fastSimpleUUID();
        this.email = email;
        this.password = EncryptUtils.getPassword(decryptPassword, email);
    }

    public static User create(String email, String decryptPassword) {
        return new User(email, decryptPassword);
    }

    public void updateInfo(String avatar, String lastName, String firstName, String nationality, IdType idType, String idNumber) {
        this.avatar = avatar;
        this.lastName = lastName;
        this.firstName = firstName;
        this.nationality = nationality;
        this.idType = idType;
        this.idNumber = idNumber;
    }

    public void changePassword(String oldPassword, String newPassword) {

        if (!password.equals(EncryptUtils.getPassword(oldPassword, email))) {
            throw new RestException(ErrorCode.FAILED, "密码错误");
        }
        this.password = EncryptUtils.getPassword(newPassword, email);
    }

}
