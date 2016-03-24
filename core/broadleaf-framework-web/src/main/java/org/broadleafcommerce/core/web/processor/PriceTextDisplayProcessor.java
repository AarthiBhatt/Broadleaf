/*
 * #%L
 * BroadleafCommerce Framework Web
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.broadleafcommerce.core.web.processor;


/**
 * A Thymeleaf processor that renders a Money object according to the currently set locale options.
 * For example, when rendering "6.99" in a US locale, the output text would be "$6.99".
 * When viewing in France for example, you might see "6,99 (US)$". Alternatively, if currency conversion
 * was enabled, you may see "5,59 (euro-symbol)"
 * 
 * @author apazzolini
 */
public class PriceTextDisplayProcessor /*extends AbstractTextChildModifierAttrProcessor*/ {

//    /**
//     * Sets the name of this processor to be used in Thymeleaf template
//     */
//    public PriceTextDisplayProcessor() {
//        super("price");
//    }
//    
//    @Override
//    public int getPrecedence() {
//        return 1500;
//    }
//
//    @Override
//    protected String getText(Arguments arguments, Element element, String attributeName) {
//        
//        Money price = null;
//
//        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
//                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue(attributeName));
//        Object result = expression.execute(arguments.getConfiguration(), arguments);
//        if (result instanceof Money) {
//            price = (Money) result;
//        } else if (result instanceof Number) {
//            price = new Money(((Number)result).doubleValue());
//        }
//
//
//        if (price == null) {
//            return "Not Available";
//        }
//
//        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
//        if (brc.getJavaLocale() != null) {
//            return BroadleafCurrencyUtils.getNumberFormatFromCache(brc.getJavaLocale(), price.getCurrency()).format(price.getAmount());
//        } else {
//            // Setup your BLC_CURRENCY and BLC_LOCALE to display a diff default.
//            return "$ " + price.getAmount().toString();
//        }
//    }
}
