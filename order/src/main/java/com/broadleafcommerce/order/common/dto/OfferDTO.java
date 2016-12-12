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
import org.broadleafcommerce.core.offer.domain.Offer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class OfferDTO  extends BaseWrapper implements APIWrapper<Offer> {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("name")
    protected String name;
    
    @JsonProperty("description")
    protected String description;
    
    @JsonProperty("marketingMessage")
    protected String marketingMessage;
    
    @JsonProperty("value")
    protected BigDecimal value;
    
    @Override
    public void wrapDetails(Offer model, HttpServletRequest request) {
        this.id = model.getId();
        this.name = model.getName();
        this.description = model.getDescription();
        this.marketingMessage = model.getMarketingMessage();
        this.value = model.getValue();
    }

    @Override
    public void wrapSummary(Offer model, HttpServletRequest request) {
        wrapDetails(model, request);
    }
}
