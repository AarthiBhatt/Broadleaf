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

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;

import com.broadleafcommerce.order.common.domain.OrderItemDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

@Data
@CommonsLog
public class OrderItemDetailDTO extends BaseWrapper implements APIWrapper<OrderItemDetail> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    protected Long id;

    @JsonProperty("externalId")
    protected Long externalId;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("salePrice")
    protected Money salePrice;

    @JsonProperty("retailPrice")
    protected Money retailPrice;

    @JsonProperty("taxable")
    protected Boolean taxable;

    @JsonProperty("taxCode")
    protected String taxCode;

    @JsonProperty("fulfillmentType")
    protected String fulfillmentType;

    @JsonProperty("width")
    protected BigDecimal width;

    @JsonProperty("height")
    protected BigDecimal height;

    @JsonProperty("depth")
    protected BigDecimal depth;

    @JsonProperty("girth")
    protected BigDecimal girth;

    @JsonProperty("size")
    protected String size;

    @JsonProperty("container")
    protected String container;

    @JsonProperty("dimensionUnitOfMeasure")
    protected String dimensionUnitOfMeasure;

    @JsonProperty("weight")
    protected BigDecimal weight;

    @JsonProperty("weightUnitOfMeasure")
    protected String weightUnitOfMeasure;

    @JsonProperty("itemDetailAttributes")
    protected JsonNode itemDetailAttributesJson;

    @JsonRawValue
    public String getItemDetailAttributesJson() {
        return itemDetailAttributesJson == null ? null :itemDetailAttributesJson.toString();
    }

    public void setItemDetailAttributesJson(JsonNode node) {
        this.itemDetailAttributesJson = node;
    }

    public boolean hasRetailPrice() {
        return getRetailPrice() != null;
    }

    public boolean hasSalePrice() {
        return getSalePrice() != null;
    }

    @Override
    public void wrapDetails(OrderItemDetail orderItemDetail, HttpServletRequest request) {
        this.id = orderItemDetail.getId();
        this.externalId = orderItemDetail.getExternalId();
        this.name = orderItemDetail.getName();
        this.salePrice = orderItemDetail.getSalePrice();
        this.retailPrice = orderItemDetail.getRetailPrice();
        this.taxable = orderItemDetail.getTaxable();
        this.taxCode = orderItemDetail.getTaxCode();

        if (orderItemDetail.getFulfillmentType() != null) {
            this.fulfillmentType = orderItemDetail.getFulfillmentType().getType();
        }

        this.width = orderItemDetail.getWidth();
        this.height = orderItemDetail.getHeight();
        this.depth = orderItemDetail.getDepth();
        this.girth = orderItemDetail.getGirth();
        this.size = orderItemDetail.getSize();
        this.container = orderItemDetail.getContainer();

        if (orderItemDetail.getDimensionUnitOfMeasure() != null) {
            this.dimensionUnitOfMeasure = orderItemDetail.getDimensionUnitOfMeasure().getType();
        }

        this.weight = orderItemDetail.getWeight();

        if (orderItemDetail.getWeightUnitOfMeasure() != null) {
            this.weightUnitOfMeasure = orderItemDetail.getWeightUnitOfMeasure().getType();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();

            if (orderItemDetail.getItemDetailAttributesJson() != null) {
                JsonNode jsonNode = mapper.readTree(orderItemDetail.getItemDetailAttributesJson());
                this.itemDetailAttributesJson = jsonNode;
            }
        } catch (IOException e) {
            log.error("Error deserializing itemDetailAttributes on item detail", e);
        }

    }

    @Override
    public void wrapSummary(OrderItemDetail orderItemDetail, HttpServletRequest request) {
        wrapDetails(orderItemDetail, request);
    }
}
