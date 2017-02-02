/*
 * #%L
 * BroadleafCommerce Order
 * %%
 * Copyright (C) 2009 - 2017 Broadleaf Commerce
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
package org.broadleafcommerce.core.offer.service;

import org.broadleafcommerce.profile.core.dto.CustomerRuleHolder;

import com.broadleafcommerce.order.common.domain.OrderCustomer;

/**
 * Created by brandon on 2/2/17.
 */
public interface CustomerRuleService {
    /**
     * Determines if the given customer passes the MVEL customer rule
     *
     * @param orderCustomer
     * @param customerRuleHolder an MVEL rule targeting Customers
     * @return true if the customer passes the rule, false otherwise
     */
    public boolean customerPassesCustomerRule(OrderCustomer orderCustomer, CustomerRuleHolder customerRuleHolder);
}
