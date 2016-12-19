/*
 * #%L
 * BroadleafCommerce Framework
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
package org.broadleafcommerce.core.order.service.call;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.PersonalMessage;

import com.broadleafcommerce.order.common.domain.OrderItemDetail;

import java.util.HashMap;
import java.util.Map;

/**
 * Only the product is required to add an item to an order.
 *
 * The category can be inferred from the product's default category.
 *
 * The sku can be inferred from either the passed in attributes as they are compared to the product's options or
 * the sku can be determined from the product's default sku.
 *
 * Personal message is optional.
 *
 */
public class OrderItemRequest {

    protected OrderItemDetail orderItemDetail;
    protected Order order;
    protected int quantity;
    protected Money salePriceOverride;
    protected Money retailPriceOverride;
    protected PersonalMessage personalMessage;
    protected Map<String,String> itemAttributes = new HashMap<String,String>();
    protected Map<String,String> additionalAttributes = new HashMap<String,String>();

    public OrderItemDetail getOrderItemDetail() {
        return orderItemDetail;
    }

    public void setOrderItemDetail(OrderItemDetail orderItemDetail) {
        this.orderItemDetail = orderItemDetail;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Map<String, String> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(Map<String, String> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }

    public Map<String, String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public Money getSalePriceOverride() {
        return salePriceOverride;
    }

    public void setSalePriceOverride(Money salePriceOverride) {
        this.salePriceOverride = salePriceOverride;
    }

    public Money getRetailPriceOverride() {
        return retailPriceOverride;
    }

    public void setRetailPriceOverride(Money retailPriceOverride) {
        this.retailPriceOverride = retailPriceOverride;
    }

    protected void copyProperties(OrderItemRequest newRequest) {
        newRequest.setItemAttributes(itemAttributes);
        newRequest.setAdditionalAttributes(additionalAttributes);
        newRequest.setPersonalMessage(personalMessage);
        newRequest.setQuantity(quantity);
        newRequest.setOrderItemDetail(orderItemDetail);
        newRequest.setOrder(order);
        newRequest.setSalePriceOverride(salePriceOverride);
        newRequest.setRetailPriceOverride(retailPriceOverride);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        OrderItemRequest that = (OrderItemRequest) o;

        if (quantity != that.quantity) return false;
        if (salePriceOverride != null ? !salePriceOverride.equals(that.salePriceOverride) : that.salePriceOverride != null)
            return false;
        if (orderItemDetail != null ? !orderItemDetail.equals(that.orderItemDetail) : that.orderItemDetail != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderItemDetail != null ? orderItemDetail.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + (salePriceOverride != null ? salePriceOverride.hashCode() : 0);
        return result;
    }

    public PersonalMessage getPersonalMessage() {
        return personalMessage;
    }

    public void setPersonalMessage(PersonalMessage personalMessage) {
        this.personalMessage = personalMessage;
    }
}
