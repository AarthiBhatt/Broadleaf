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

import org.broadleafcommerce.common.rule.MvelHelper;
import org.broadleafcommerce.profile.core.dto.CustomerRuleHolder;

import com.broadleafcommerce.order.common.domain.OrderCustomer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brandon on 2/2/17.
 */
public class CustomerRuleServiceImpl implements CustomerRuleService {

    @Override
    public boolean customerPassesCustomerRule(OrderCustomer orderCustomer, CustomerRuleHolder customerRuleHolder) {
        String customerRule = customerRuleHolder.getCustomerRule();
        Map<String, Object> ruleParams = buildCustomerRuleParams(orderCustomer);
        return customerRule == null || MvelHelper.evaluateRule(customerRule, ruleParams);
    }

    protected Map<String, Object> buildCustomerRuleParams(OrderCustomer orderCustomer) {
        HashMap<String, Object> vars = new HashMap<>();
        vars.put("customer", orderCustomer);
        return vars;
    }
}
