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

import com.broadleafcommerce.order.common.domain.OrderCustomer;
import com.broadleafcommerce.order.common.service.OrderCustomerService;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

/**
 * Created by brandon on 12/6/16.
 */
@Data
public class OrderCustomerDTO extends BaseWrapper implements APIWrapper<OrderCustomer>, APIUnwrapper<OrderCustomer> {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("externalId")
    protected Long externalId;
    
    @JsonProperty("firstName")
    protected String firstName;
    
    @JsonProperty("lastName")
    protected String lastName;
    
    @JsonProperty("emailAddress")
    protected String emailAddress;
    
    @JsonProperty("taxExempt")
    protected Boolean isTaxExempt = false;
    
    @JsonProperty("taxExemptionCode")
    protected String taxExemptionCode;
    
    @JsonProperty("customerAttributesJson")
    protected String customerAttributesJson;
    
    @Override
    public OrderCustomer unwrap(HttpServletRequest request, ApplicationContext context) {
        OrderCustomerService customerService = (OrderCustomerService) context.getBean("blOrderCustomerService");
        OrderCustomer customer = customerService.create();
        customer.setId(this.getId());
        customer.setExternalId(this.getExternalId());
        customer.setFirstName(this.getFirstName());
        customer.setLastName(this.getLastName());
        customer.setEmailAddress(this.getEmailAddress());
        customer.setCustomerAttributesJson(this.getCustomerAttributesJson());
        customer.setTaxExempt(this.getIsTaxExempt());
        customer.setTaxExemptionCode(this.getTaxExemptionCode());
        return customer;
    }

    @Override
    public void wrapDetails(OrderCustomer customer, HttpServletRequest request) {
        this.id = customer.getId();
        this.externalId = customer.getExternalId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.emailAddress = customer.getEmailAddress();
        this.isTaxExempt = customer.getTaxExempt();
        this.taxExemptionCode = customer.getTaxExemptionCode();
        this.customerAttributesJson = customer.getCustomerAttributesJson();
    }

    @Override
    public void wrapSummary(OrderCustomer customer, HttpServletRequest request) {
        wrapDetails(customer, request);
    }

}
