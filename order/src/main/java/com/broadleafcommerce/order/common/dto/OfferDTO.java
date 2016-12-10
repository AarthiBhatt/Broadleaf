package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.core.offer.domain.Offer;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NonNull;

@Data
public class OfferDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Long id;
    public String name;
    public String description;
    public String marketingMessage;
    public BigDecimal value;
    
    public OfferDTO(@NonNull Offer offer) {
        this.id = offer.getId();
        this.name = offer.getName();
        this.description = offer.getDescription();
        this.marketingMessage = offer.getMarketingMessage();
        this.value = offer.getValue();
    }
}
