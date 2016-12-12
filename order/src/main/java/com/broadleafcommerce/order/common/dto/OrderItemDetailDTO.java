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

import org.broadleafcommerce.common.money.Money;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItemDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long externalId;
    protected String name;
    protected Money salePrice;
    protected Money retailPrice;
    protected Boolean taxable;
    protected String taxCode;
    protected String fulfillmentType;
    protected BigDecimal width;
    protected BigDecimal height;
    protected BigDecimal depth;
    protected BigDecimal girth;
    protected String size;
    protected String container;
    protected String dimensionUnitOfMeasure;
    protected BigDecimal weight;
    protected String weightUnitOfMeasure;
    protected String skuAttributesJson;

    public boolean hasRetailPrice() {
        return getRetailPrice() != null;
    }

    public boolean hasSalePrice() {
        return getSalePrice() != null;
    }
}
