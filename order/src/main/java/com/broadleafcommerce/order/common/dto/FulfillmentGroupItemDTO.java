/*
 * #%L
 * BroadleafCommerce Order
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class FulfillmentGroupItemDTO extends BaseWrapper implements APIWrapper<FulfillmentGroupItem> {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("orderItem")
    protected OrderItemDTO orderItem;
    
    @JsonProperty("quantity")
    protected Integer quantity;
    
    @JsonProperty("retailPrice")
    protected Money retailPrice;
    
    @JsonProperty("salePrice")
    protected Money salePrice;
    
    @JsonProperty("totalItemAmount")
    protected Money totalItemAmount;
    
    @JsonProperty("status")
    protected String status;
    
    @JsonProperty("totalTax")
    protected Money totalTax;
    
    @Override
    public void wrapDetails(FulfillmentGroupItem fgi, HttpServletRequest request) {
        setId(fgi.getId());;
        if (fgi.getOrderItem() != null) {
            OrderItemDTO oi = (OrderItemDTO) context.getBean(OrderItemDTO.class.getName());
            oi.wrapDetails(fgi.getOrderItem(), request);
            setOrderItem(oi);
        }
        setQuantity(fgi.getQuantity());
        setRetailPrice(fgi.getRetailPrice());
        setSalePrice(fgi.getSalePrice());
        setTotalItemAmount(fgi.getTotalItemAmount());
        setTotalTax(fgi.getTotalTax());
        if (fgi.getStatus() != null) {
            setStatus(fgi.getStatus().getType());
        }
    }

    @Override
    public void wrapSummary(FulfillmentGroupItem fgi, HttpServletRequest request) {
        wrapDetails(fgi, request);
    }

    
}
