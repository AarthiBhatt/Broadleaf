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

import org.broadleafcommerce.common.api.APIUnwrapper;
import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.springframework.context.ApplicationContext;

import com.broadleafcommerce.order.common.domain.OrderAddress;
import com.broadleafcommerce.order.common.service.OrderAddressService;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

/**
 * Created by brandon on 12/6/16.
 */
@Data
public class OrderAddressDTO extends BaseWrapper implements APIWrapper<OrderAddress>, APIUnwrapper<OrderAddress> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("externalId")
    protected Long externalId;
    
    @JsonProperty("fullName")
    protected String fullName;
    
    @JsonProperty("firstName")
    protected String firstName;
    
    @JsonProperty("lastName")
    protected String lastName;
    
    @JsonProperty("companyName")
    protected String companyName;
    
    @JsonProperty("addressLine1")
    protected String addressLine1;
    
    @JsonProperty("addressLine2")
    protected String addressLine2;
    
    @JsonProperty("addressLine3")
    protected String addressLine3;
    
    @JsonProperty("cityLocality")
    protected String cityLocality;
    
    @JsonProperty("stateProvinceRegion")
    protected String stateProvinceRegion;
    
    @JsonProperty("countryCode")
    protected String countryCode;
    
    @JsonProperty("postalCode")
    protected String postalCode;
    
    @JsonProperty("phone")
    protected String phone;
    
    @JsonProperty("emailAddress")
    protected String emailAddress;
    
    @Override
    public OrderAddress unwrap(HttpServletRequest request, ApplicationContext context) {
        OrderAddressService addressService = (OrderAddressService) context.getBean("blOrderAddressService");
        OrderAddress address = addressService.create();
        address.setId(this.getId());
        address.setExternalId(this.getExternalId());
        address.setAddressLine1(this.getAddressLine1());
        address.setAddressLine2(this.getAddressLine2());
        address.setAddressLine3(this.getAddressLine3());
        address.setCityLocality(this.getCityLocality());
        address.setCountryCode(this.getCountryCode());
        address.setEmailAddress(this.getEmailAddress());
        address.setFullName(this.getFullName());
        address.setFirstName(this.getFirstName());
        address.setLastName(this.getLastName());
        address.setPhone(this.getPhone());
        address.setPostalCode(this.getPostalCode());
        address.setStateProvinceRegion(this.getStateProvinceRegion());
        return address;
    }

    @Override
    public void wrapDetails(OrderAddress address, HttpServletRequest request) {
        this.id = address.getId();
        this.externalId = address.getExternalId();
        this.fullName = address.getFullName();
        this.firstName = address.getFirstName();
        this.lastName = address.getLastName();
        this.companyName = address.getCompanyName();
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.addressLine3 = address.getAddressLine3();
        this.cityLocality = address.getCityLocality();
        this.stateProvinceRegion = address.getStateProvinceRegion();
        this.countryCode = address.getCountryCode();
        this.postalCode = address.getPostalCode();
        this.phone = address.getPhone();
        this.emailAddress = address.getEmailAddress();
    }

    @Override
    public void wrapSummary(OrderAddress address, HttpServletRequest request) {
        wrapDetails(address, request);
    }

}
