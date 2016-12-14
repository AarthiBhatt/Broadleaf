package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class FulfillmentOptionDTO extends BaseWrapper implements APIWrapper<FulfillmentOption>{
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("name")
    protected String name;
    
    @JsonProperty("fulfillmentType")
    protected String fulfillmentType;

    @Override
    public void wrapDetails(FulfillmentOption fo, HttpServletRequest request) {
        setId(fo.getId());
        setName(fo.getName());
        if (fo.getFulfillmentType() != null) {
            setFulfillmentType(fo.getFulfillmentType().getType());
        }
    }

    @Override
    public void wrapSummary(FulfillmentOption fo, HttpServletRequest request) {
        wrapDetails(fo, request);
    }

}
