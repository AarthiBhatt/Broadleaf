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
