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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FulfillmentOrderDTO {

    protected Long id;
    
    protected String externalId;
    
    protected Integer sequence;
    
    protected String fulfillmentOrderNumber;
    
    //Maps to fulfillmentGroup
    protected Long fulfillmentGroupId;
    
    //Maps to parentFulfillmentOrder
    protected Long parentFulfillmentOrderId;
    
    //Maps to option (FulfillmentOption)
    protected Long optionId;
    
    @Deprecated
    protected String shipperType;
    
    @Deprecated
    protected String trackingNumber;
    
    protected String status;
    
    protected List<FulfillmentOrderItemDTO> itemIds = new ArrayList<>();
    
    protected List<ShipmentDetailDTO> shipmentDetails = new ArrayList<>();
    
    protected String cancellationReasonType;
    
    protected BigDecimal total;
    
    @Deprecated
    protected Date expectedShipDate;
    
    @Deprecated
    protected Date actualShipDate;
    
    Map<String, Object> fulfillmentOrderProperties = new HashMap<>();

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getExternalId() {
        return externalId;
    }
    
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getFulfillmentOrderNumber() {
        return fulfillmentOrderNumber;
    }
    
    public void setFulfillmentOrderNumber(String fulfillmentOrderNumber) {
        this.fulfillmentOrderNumber = fulfillmentOrderNumber;
    }

    public Long getFulfillmentGroupId() {
        return fulfillmentGroupId;
    }
    
    public void setFulfillmentGroupId(Long fulfillmentGroupId) {
        this.fulfillmentGroupId = fulfillmentGroupId;
    }
    
    public Long getParentFulfillmentOrderId() {
        return parentFulfillmentOrderId;
    }
    
    public void setParentFulfillmentOrderId(Long parentFulfillmentOrderId) {
        this.parentFulfillmentOrderId = parentFulfillmentOrderId;
    }
    
    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getShipperType() {
        return shipperType;
    }
    
    public void setShipperType(String shipperType) {
        this.shipperType = shipperType;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FulfillmentOrderItemDTO> getItemIds() {
        return itemIds;
    }
    
    public void setItemIds(List<FulfillmentOrderItemDTO> itemIds) {
        this.itemIds = itemIds;
    }
    
    public List<ShipmentDetailDTO> getShipmentDetails() {
        return shipmentDetails;
    }
    
    public void setShipmentDetails(List<ShipmentDetailDTO> shipmentDetails) {
        this.shipmentDetails = shipmentDetails;
    }
    
    public String getCancellationReasonType() {
        return cancellationReasonType;
    }

    public void setCancellationReasonType(String cancellationReasonType) {
        this.cancellationReasonType = cancellationReasonType;
    }
    
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getExpectedShipDate() {
        return expectedShipDate;
    }
    
    public void setExpectedShipDate(Date expectedShipDate) {
        this.expectedShipDate = expectedShipDate;
    }
    
    public Date getActualShipDate() {
        return actualShipDate;
    }

    public void setActualShipDate(Date actualShipDate) {
        this.actualShipDate = actualShipDate;
    }

    public Map<String, Object> getFulfillmentOrderProperties() {
        return fulfillmentOrderProperties;
    }
    
    public void setFulfillmentOrderProperties(Map<String, Object> fulfillmentOrderProperties) {
        this.fulfillmentOrderProperties = fulfillmentOrderProperties;
    }
    
}
