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

import com.broadleafcommerce.order.common.domain.OrderCustomer;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by brandon on 12/6/16.
 */
@Data
@NoArgsConstructor
public class OrderCustomerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;
    protected Long externalId;
    protected String firstName;
    protected String lastName;
    protected String emailAddress;
    protected Boolean isTaxExempt = false;
    protected String taxExemptionCode;
    protected String customerAttributesJson;
    
    public OrderCustomerDTO(@NonNull OrderCustomer customer) {
        this.id = customer.getId();
        this.externalId = customer.getExternalId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.emailAddress = customer.getEmailAddress();
        this.isTaxExempt = customer.getTaxExempt();
        this.taxExemptionCode = customer.getTaxExemptionCode();
        this.customerAttributesJson = customer.getCustomerAttributesJson();
    }

}
