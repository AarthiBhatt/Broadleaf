package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.core.offer.domain.OfferCode;

import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

@Data
public class OfferCodeDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public Long id;
    public OfferDTO offer;
    public String offerCode;
    
    public OfferCodeDTO(@NonNull OfferCode offerCode) {
        this.id = offerCode.getId();
        if (offerCode.getOffer() != null) {
            this.offer = new OfferDTO(offerCode.getOffer());
        }
        this.offerCode = offerCode.getOfferCode();
    }
}
