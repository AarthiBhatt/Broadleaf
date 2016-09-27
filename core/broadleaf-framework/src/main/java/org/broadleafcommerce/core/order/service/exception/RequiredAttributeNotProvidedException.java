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
package org.broadleafcommerce.core.order.service.exception;

import java.util.List;

/**
 * This runtime exception will be thrown when an attempt to add to cart without specifying
 * all required product options has been made.
 * 
 * @author apazzolini
 */
public class RequiredAttributeNotProvidedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected List<String> attributeNames;

    public RequiredAttributeNotProvidedException(String message, String attributeName) {
        super(message);
        setAttributeName(attributeName);
    }

    public RequiredAttributeNotProvidedException(String message, String attributeName, Throwable cause) {
        super(message, cause);
        setAttributeName(attributeName);
    }

    public RequiredAttributeNotProvidedException(String attributeName) {
        super("The attribute " + attributeName + " was not provided");
        setAttributeName(attributeName);
    }

    public RequiredAttributeNotProvidedException(List<String> attributeNames, String message) {
        super(message);
        setAttributeName(attributeNames);
    }

    public String getAttributeName() {
        return attributeNames.get(0);
    }

    public void setAttributeName(String attributeName) {
        this.attributeNames.add(attributeName);
    }

    public void setAttributeName(List<String> attributeNames) {
        this.attributeNames.addAll(attributeNames);
    }


}
