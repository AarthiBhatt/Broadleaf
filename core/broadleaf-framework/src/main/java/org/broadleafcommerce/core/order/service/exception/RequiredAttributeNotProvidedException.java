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

    protected String attributeName;
    protected List<String> errorList;

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

    public RequiredAttributeNotProvidedException(List<String> errorList, String message) {
        super(message);
        this.setErrorList(errorList);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }


}
