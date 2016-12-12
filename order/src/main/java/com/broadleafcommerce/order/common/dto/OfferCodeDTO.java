package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.core.offer.domain.OfferCode;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class OfferCodeDTO extends BaseWrapper implements APIWrapper<OfferCode>{
    
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("offer")
    protected OfferDTO offer;
    
    @JsonProperty("offerCode")
    protected String offerCode;
    
    @Override
    public void wrapDetails(OfferCode model, HttpServletRequest request) {
        this.id = model.getId();
        if (model.getOffer() != null) {
            OfferDTO offer = (OfferDTO) context.getBean(OfferDTO.class.getName());
            offer.wrapDetails(model.getOffer(), request);
            this.offer = offer;
        }
        this.offerCode = model.getOfferCode();
        
    }

    @Override
    public void wrapSummary(OfferCode model, HttpServletRequest request) {
        wrapDetails(model, request);
    }
}
