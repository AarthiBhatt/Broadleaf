/*
 * #%L
 * BroadleafCommerce Framework
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
package com.broadleafcommerce.order.common.domain;

import org.broadleafcommerce.core.payment.domain.CustomerPayment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrderCustomer {

    public Long getId();
    
    public Long getExternalId();
    
    public String getFirstName();
    
    public String getLastName();
    
    public String getEmailAddress();
    
    public List<CustomerPayment> getCustomerPayments();
    
    public boolean isAnonymous();

    public void setId(Long id);
    
    public void setExternalId(Long externalId);
    
    public void setEmailAddress(String emailAddress);

    public void setLastName(String lastName);

    public void setFirstName(String firstName);
    
    public void setCustomerPayments(List<CustomerPayment> customerPayments);

    Boolean getTaxExempt();

    void setTaxExempt(Boolean taxExempt);

    String getTaxExemptionCode();

    void setTaxExemptionCode(String taxExemptionCode);

    String getCustomerAttributesJson();

    void setCustomerAttributesJson(String customerAttributesJson);

    public Map<String, Object> getCustomerAttributesMap() throws IOException;

    public void setAnonymous(boolean anonymous);

}
