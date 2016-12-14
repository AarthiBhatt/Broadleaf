package com.broadleafcommerce.order.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

@Data
public class SplitFulfillmentGroupDTO {

    @JsonProperty("orderItems")
    protected List<Long> orderItemIds;
    
    @JsonProperty("address")
    protected OrderAddressDTO orderAddress;
}
