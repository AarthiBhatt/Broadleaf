/*
 * #%L
 * BroadleafCommerce Micro Common
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
package org.broadleafcommerce.common.web.expression;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.util.StringUtil;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.broadleafcommerce.presentation.condition.ConditionalOnTemplating;
import org.broadleafcommerce.presentation.expression.BroadleafVariableExpression;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component("blPriceVariableExpression")
@ConditionalOnTemplating
public class PriceVariableExpression implements BroadleafVariableExpression {

    @Override
    public String getName() {
        return "price";
    }

    /**
     * Returns the price at the correct scale and rounding for the default currency
     * @see Money#defaultCurrency()
     * @param amount
     * @return
     */
    public String getPrice(String amount) {
        Money price = Money.ZERO;
        String sanitizedAmount = StringUtil.removeNonNumerics(amount);
        if (StringUtils.isNotEmpty(sanitizedAmount)) {
            price = new Money(sanitizedAmount);
            BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
            if (brc.getJavaLocale() != null) {
                NumberFormat formatter = BroadleafCurrencyUtils.getNumberFormatFromCache(brc.getJavaLocale(), price.getCurrency());
                return formatter.format(price.getAmount());
            }
        }
        return "$ " + price.getAmount().toString();
    }
}
