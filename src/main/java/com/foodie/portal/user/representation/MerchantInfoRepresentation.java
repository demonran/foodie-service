package com.foodie.portal.user.representation;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MerchantInfoRepresentation {
    private String avatar;
    private String name;
    private String description;
    private Double balance;
    //未结算
    private Double openAccount;
    //累计收益
    private Double accumulatedEarnings;
    private String withdrawAccount;
    private String withdrawName;
    private int orderCount;

}
