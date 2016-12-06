/*
 * #%L
 * BroadleafCommerce Framework Web
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
package org.broadleafcommerce.core.web.checkout.model;

import org.broadleafcommerce.core.order.domain.OrderAddress;
import org.broadleafcommerce.core.order.domain.OrderAddressImpl;
import org.broadleafcommerce.core.payment.domain.CustomerPayment;

import java.io.Serializable;

/**
 * <p>A form to model adding the Billing Address to the Order</p>
 *
 * @author Elbert Bautista (elbertbautista)
 * @author Brian Polster (bpolster)
 */
public class BillingInfoForm implements Serializable {

    private static final long serialVersionUID = 1L;

    protected OrderAddress address = new OrderAddressImpl();
    protected boolean useShippingAddress;
    protected Long customerPaymentId;
    protected CustomerPayment customerPayment;
    protected Boolean saveNewPayment = true;
    protected Boolean useCustomerPayment = false;
    protected String paymentName;

    public BillingInfoForm() {
        address.setPhone(null);
    }

    public OrderAddress getAddress() {
        return address;
    }

    public void setAddress(OrderAddress address) {
        this.address = address;
    }

    public boolean isUseShippingAddress() {
        return useShippingAddress;
    }

    public void setUseShippingAddress(boolean useShippingAddress) {
        this.useShippingAddress = useShippingAddress;
    }

    public Long getCustomerPaymentId() {
        return customerPaymentId;
    }

    public void setCustomerPaymentId(Long customerPaymentId) {
        this.customerPaymentId = customerPaymentId;
    }

    public CustomerPayment getCustomerPayment() {
        return customerPayment;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public Boolean getUseCustomerPayment() {
        return useCustomerPayment == null? false : useCustomerPayment;
    }

    public void setUseCustomerPayment(Boolean useCustomerPayment) {
        this.useCustomerPayment = useCustomerPayment == null ? false : useCustomerPayment;
    }

    public Boolean getSaveNewPayment() {
        return saveNewPayment == null? false : saveNewPayment;
    }

    public void setSaveNewPayment(Boolean saveNewPayment) {
        this.saveNewPayment = saveNewPayment == null ? false : saveNewPayment;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
}
