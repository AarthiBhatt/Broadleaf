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

import org.apache.commons.collections4.ListUtils;
import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class FulfillmentGroupDTO extends BaseWrapper implements APIWrapper<FulfillmentGroup> {
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
    
    @JsonProperty("fulfillmentGroupItems")
    protected List<FulfillmentGroupItemDTO> fulfillmentGroupItems;
    
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
            OrderAddressDTO oa = (OrderAddressDTO) context.getBean(OrderAddressDTO.class.getName());
            oa.wrapDetails(fg.getAddress(), request);
            setOrderAddressDto(oa);
        }
        List<FulfillmentGroupItemDTO> fulfillmentGroupItemDtos = new ArrayList<>();
        for (FulfillmentGroupItem fgi : ListUtils.emptyIfNull(fg.getFulfillmentGroupItems())) {
            FulfillmentGroupItemDTO fgiDto = (FulfillmentGroupItemDTO) context.getBean(FulfillmentGroupItemDTO.class.getName());
            fgiDto.wrapDetails(fgi, request);
            fulfillmentGroupItemDtos.add(fgiDto);
        }
        setFulfillmentGroupItems(fulfillmentGroupItemDtos);
    }

    @Override
    public void wrapSummary(FulfillmentGroup fg, HttpServletRequest request) {
        wrapDetails(fg, request);
    }

}
