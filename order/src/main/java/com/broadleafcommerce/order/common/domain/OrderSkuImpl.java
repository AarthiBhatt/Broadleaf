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
package com.broadleafcommerce.order.common.domain;


import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.time.domain.TemporalTimestampListener;
import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_SKU")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
public class OrderSkuImpl implements OrderSku {

    @Id
    @GeneratedValue(generator = "OrderSkuId")
    @GenericGenerator(
            name = "OrderSkuId",
            strategy = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name = "segment_value", value = "OrderSkuImpl"),
                    @Parameter(name = "entity_name", value = "com.broadleafcommerce.order.common.domain.OrderSkuImpl")
            }
    )
    @Column(name = "ORDER_SKU_ID")
    protected Long id;

    @Column(name = "EXTERNAL_ID")
    @Index(name="ORDER_SKU_EXTERNAL_ID_INDEX", columnNames={"EXTERNAL_ID"})
    protected Long externalId;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "SALE_PRICE", precision = 19, scale = 5)
    protected Money salePrice;

    @Column(name = "RETAIL_PRICE", precision = 19, scale = 5)
    protected Money retailPrice;

    @Column(name = "TAXABLE_FLAG")
    protected Boolean taxable;

    @Column(name = "TAX_CODE")
    protected String taxCode;

    @Column(name = "FULFILLMENT_TYPE")
    protected String fulfillmentType;

    //Dimension information
    @Column(name = "WIDTH")
    protected BigDecimal width;

    @Column(name = "HEIGHT")
    protected BigDecimal height;

    @Column(name = "DEPTH")
    protected BigDecimal depth;

    @Column(name = "GIRTH")
    protected BigDecimal girth;

    @Column(name = "CONTAINER_SIZE")
    protected String size;

    @Column(name = "CONTAINER_SHAPE")
    protected String container;

    @Column(name = "DIMENSION_UNIT_OF_MEASURE")
    protected String dimensionUnitOfMeasure;

    //Weight information
    @Column(name = "WEIGHT")
    protected BigDecimal weight;

    @Column(name = "WEIGHT_UNIT_OF_MEASURE")
    protected String weightUnitOfMeasure;

    @Lob
    @Column(name = "SKU_ATTRIBUTES_JSON", length = Integer.MAX_VALUE - 1)
    protected String skuAttributesJson;

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    @Override
    public Long getExternalId() {
        return externalId;
    }

    @Override
    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { name = name; }

    @Override
    public Money getRetailPrice() {
        return retailPrice;
    }

    @Override
    public void setRetailPrice(Money retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Override
    public Money getSalePrice() { return salePrice; }

    @Override
    public void setSalePrice(Money salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public Boolean getTaxable() {
        return taxable;
    }

    @Override
    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    @Override
    public String getTaxCode() {
        return taxCode;
    }

    @Override
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public FulfillmentType getFulfillmentType() {
        return FulfillmentType.getInstance(this.fulfillmentType);
    }

    @Override
    public void setFulfillmentType(FulfillmentType fulfillmentType) {
        if (fulfillmentType != null) {
            this.fulfillmentType = fulfillmentType.getType();
        }
    }

    @Override
    public BigDecimal getWidth() {
        return width;
    }

    @Override
    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @Override
    public BigDecimal getHeight() {
        return height;
    }

    @Override
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Override
    public BigDecimal getDepth() {
        return depth;
    }

    @Override
    public void setDepth(BigDecimal depth) {
        this.depth = depth;
    }

    @Override
    public BigDecimal getGirth() {
        return girth;
    }

    @Override
    public void setGirth(BigDecimal girth) {
        this.girth = girth;
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String getContainer() {
        return container;
    }

    @Override
    public void setContainer(String container) {
        this.container = container;
    }

    @Override
    public String getDimensionUnitOfMeasure() {
        return dimensionUnitOfMeasure;
    }

    @Override
    public void setDimensionUnitOfMeasure(String dimensionUnitOfMeasure) { this.dimensionUnitOfMeasure = dimensionUnitOfMeasure; }

    @Override
    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public WeightUnitOfMeasureType getWeightUnitOfMeasure() { return WeightUnitOfMeasureType.getInstance(this.weightUnitOfMeasure); }

    @Override
    public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure) {
        if (weightUnitOfMeasure != null) {
            this.weightUnitOfMeasure = weightUnitOfMeasure.getType();
        }
    }

    @Override
    public String getSkuAttributesJson() {
        return skuAttributesJson;
    }

    @Override
    public void setSkuAttributesJson(String skuAttributesJson) {
        this.skuAttributesJson = skuAttributesJson;
    }
}
