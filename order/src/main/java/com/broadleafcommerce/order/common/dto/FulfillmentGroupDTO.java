package com.broadleafcommerce.order.common.dto;

import org.broadleafcommerce.common.api.APIUnwrapper;
import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.order.service.FulfillmentOptionService;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;
import org.springframework.context.ApplicationContext;

import com.broadleafcommerce.order.common.domain.OrderAddress;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class FulfillmentGroupDTO extends BaseWrapper implements APIWrapper<FulfillmentGroup>, APIUnwrapper<FulfillmentGroup> {
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("referenceNumber")
    protected String referenceNumber;
    
    @JsonProperty("retailFulfillmentPrice")
    protected Money retailFulfillmentPrice;
    
    @JsonProperty("saleFulfillmentPrice")
    protected Money saleFulfillmentPrice;
    
    @JsonProperty("fulfillmentPrice")
    protected Money fulfillmentPrice;
    
    @JsonProperty("fulfillmentType")
    protected String fulfillmentType;
    
    @JsonProperty("totalTax")
    protected Money totalTax;
    
    @JsonProperty("totalItemTax")
    protected Money totalItemTax;
    
    @JsonProperty("total")
    protected Money total;
    
    @JsonProperty("fulfillmentOption")
    protected FulfillmentOptionDTO fulfillmentOptionDto;
    
    @JsonProperty("address")
    protected OrderAddressDTO orderAddressDto;
    
    @Override
    public FulfillmentGroup unwrap(HttpServletRequest request, ApplicationContext context) {
        FulfillmentGroupService fulfillmentGroupService = (FulfillmentGroupService) context.getBean("blFulfillmentGroupService");
        FulfillmentOptionService fulfillmentOptionService = (FulfillmentOptionService) context.getBean("blFulfillmentOptionService");
        FulfillmentGroup fg = fulfillmentGroupService.createEmptyFulfillmentGroup();
        fg.setId(getId());
        fg.setReferenceNumber(getReferenceNumber());
        fg.setRetailFulfillmentPrice(getRetailFulfillmentPrice());
        fg.setSaleFulfillmentPrice(getSaleFulfillmentPrice());
        fg.setFulfillmentPrice(getFulfillmentPrice());
        if (getFulfillmentType() != null) {
            fg.setType(FulfillmentType.getInstance(getFulfillmentType()));
        }
        fg.setTotalTax(getTotalTax());
        fg.setTotalItemTax(getTotalItemTax());
        fg.setTotal(getTotal());
        if (getFulfillmentOptionDto() != null) {
            fg.setFulfillmentOption(fulfillmentOptionService.readFulfillmentOptionById(getFulfillmentOptionDto().getId()));
        }
        if (getOrderAddressDto() != null) {
            fg.setAddress(getOrderAddressDto().unwrap(request, context));
        }
        return fg;
    }

    @Override
    public void wrapDetails(FulfillmentGroup fg, HttpServletRequest request) {
        setId(fg.getId());
        setReferenceNumber(fg.getReferenceNumber());
        setRetailFulfillmentPrice(fg.getRetailFulfillmentPrice());
        setSaleFulfillmentPrice(fg.getSaleFulfillmentPrice());
        setFulfillmentPrice(fg.getFulfillmentPrice());
        if (fg.getType() != null) {
            setFulfillmentType(fg.getType().getType());
        }
        setTotalTax(fg.getTotalTax());
        setTotalItemTax(fg.getTotalItemTax());
        setTotal(fg.getTotal());
        if (fg.getFulfillmentOption() != null) {
            FulfillmentOptionDTO fo = (FulfillmentOptionDTO) context.getBean(FulfillmentOptionDTO.class.getName());
            fo.wrapDetails(fg.getFulfillmentOption(), request);
            setFulfillmentOptionDto(fo);
        }
        if (fg.getAddress() != null) {
            OrderAddressDTO oa = (OrderAddressDTO) context.getBean(OrderAddress.class.getName());
            oa.wrapDetails(fg.getAddress(), request);
            setOrderAddressDto(oa);
        }
    }

    @Override
    public void wrapSummary(FulfillmentGroup fg, HttpServletRequest request) {
        wrapDetails(fg, request);
    }

}
