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
import org.broadleafcommerce.core.order.domain.OrderItem;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class OrderItemDTO extends BaseWrapper implements APIWrapper<OrderItem> {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;

    @JsonProperty("itemDetail")
    protected OrderItemDetailDTO orderItemDetailDTO;

    @JsonProperty("quantity")
    protected int quantity;
    
    @JsonProperty("retailPrice")
    protected Money retailPrice;
    
    @JsonProperty("salePrice")
    protected Money salePrice;
    
    @JsonProperty("name")
    protected String name;
    
    @JsonProperty("itemTaxable")
    protected Boolean itemTaxable;
    
    @JsonProperty("hasRetailPriceOverride")
    protected Boolean retailPriceOverride;
    
    @JsonProperty("hasSalePriceOverride")
    protected Boolean salePriceOverride;

    @Override
    public void wrapDetails(OrderItem item, HttpServletRequest request) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.retailPrice = item.getRetailPrice();
        this.salePrice = item.getSalePrice();
        this.name = item.getName();
        this.itemTaxable = item.getIsDiscounted();
        this.retailPriceOverride = item.isRetailPriceOverride();
        this.salePriceOverride = item.isSalePriceOverride();

        if (item.getOrderItemDetail() != null) {
            OrderItemDetailDTO detail = (OrderItemDetailDTO) context.getBean(OrderItemDetailDTO.class.getName());
            this.orderItemDetailDTO = detail;
            this.orderItemDetailDTO.wrapDetails(item.getOrderItemDetail(), request);
        }
    }

    @Override
    public void wrapSummary(OrderItem item, HttpServletRequest request) {
        wrapDetails(item, request);
    }
}
