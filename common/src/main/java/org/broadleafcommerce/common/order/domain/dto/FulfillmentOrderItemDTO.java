/*
 * #%L
 * BroadleafCommerce Common Libraries
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
package org.broadleafcommerce.common.order.domain.dto;

import java.math.BigDecimal;

public class FulfillmentOrderItemDTO {

    protected Long id;
    
    protected Long fulfillmentGroupItemId;
    
    protected Long fulfillmentOrderId;
    
    protected Integer quantity;
    
    protected Integer authorizedQuantityReturned;
    
    protected Integer confirmedQuantityReturned;
    
    protected BigDecimal fulfillmentItemAmount;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getFulfillmentGroupItemId() {
        return fulfillmentGroupItemId;
    }

    public void setFulfillmentGroupItemId(Long fulfillmentGroupItemId) {
        this.fulfillmentGroupItemId = fulfillmentGroupItemId;
    }

    public Long getFulfillmentOrderId() {
        return fulfillmentOrderId;
    }

    public void setFulfillmentOrderId(Long fulfillmentOrderId) {
        this.fulfillmentOrderId = fulfillmentOrderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Integer getAuthorizedQuantityReturned() {
        return authorizedQuantityReturned;
    }

    public void setAuthorizedQuantityReturned(Integer authorizedQuantityReturned) {
        this.authorizedQuantityReturned = authorizedQuantityReturned;
    }
    
    public Integer getConfirmedQuantityReturned() {
        return confirmedQuantityReturned;
    }

    public void setConfirmedQuantityReturned(Integer confirmedQuantityReturned) {
        this.confirmedQuantityReturned = confirmedQuantityReturned;
    }
    
    public BigDecimal getFulfillmentItemAmount() {
        return fulfillmentItemAmount;
    }
    
    public void setFulfillmentItemAmount(BigDecimal fulfillmentItemAmount) {
        this.fulfillmentItemAmount = fulfillmentItemAmount;
    }
    
}
