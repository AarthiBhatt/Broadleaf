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
import org.broadleafcommerce.common.util.DimensionUnitOfMeasureType;
import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;

import java.math.BigDecimal;

public interface OrderItemDetail {

    public Long getId();

    public void setId(Long id);

    public Long getExternalId();

    public void setExternalId(Long externalId);

    public String getName();

    public void setName(String name);

    public Money getRetailPrice();

    public void setRetailPrice(Money retailPrice);

    public Money getSalePrice();

    public void setSalePrice(Money salePrice);

    public Boolean getTaxable();

    public void setTaxable(Boolean taxable);

    public String getTaxCode();

    public void setTaxCode(String taxCode);

    public FulfillmentType getFulfillmentType();

    public void setFulfillmentType(FulfillmentType fulfillmentType);

    public BigDecimal getWidth();

    public void setWidth(BigDecimal width);

    public BigDecimal getHeight();

    public void setHeight(BigDecimal height);

    public BigDecimal getDepth();

    public void setDepth(BigDecimal depth);

    public BigDecimal getGirth();

    public void setGirth(BigDecimal girth);

    public String getSize();

    public void setSize(String size);

    public String getContainer();

    public void setContainer(String container);

    public DimensionUnitOfMeasureType getDimensionUnitOfMeasure();

    public void setDimensionUnitOfMeasure(DimensionUnitOfMeasureType dimensionUnitOfMeasure);

    public BigDecimal getWeight();

    public void setWeight(BigDecimal weight);

    public WeightUnitOfMeasureType getWeightUnitOfMeasure();

    public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure);

    public String getSkuAttributesJson();

    public void setSkuAttributesJson(String skuAttributesJson);
}
