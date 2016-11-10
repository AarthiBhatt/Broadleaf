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

import java.util.Date;

public class ShipmentDetailDTO {

    protected Long id;
    
    protected long fulfillmentOrderId;
    
    protected String shipperType;
    
    protected String serviceCode;
    
    protected String trackingNumber;

    protected Date expectedShipDate;
    
    protected Date actualShipDate;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public long getFulfillmentOrderId() {
        return fulfillmentOrderId;
    }

    public void setFulfillmentOrderId(long fulfillmentOrderId) {
        this.fulfillmentOrderId = fulfillmentOrderId;
    }
    
    public String getShipperType() {
        return shipperType;
    }
    
    public void setShipperType(String shipperType) {
        this.shipperType = shipperType;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
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
    
}
