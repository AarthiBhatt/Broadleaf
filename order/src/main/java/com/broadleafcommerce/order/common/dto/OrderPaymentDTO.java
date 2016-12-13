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

import org.apache.commons.collections4.ListUtils;
import org.broadleafcommerce.common.api.APIUnwrapper;
import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.payment.PaymentGatewayType;
import org.broadleafcommerce.common.payment.PaymentType;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.payment.domain.OrderPayment;
import org.broadleafcommerce.core.payment.domain.PaymentTransaction;
import org.broadleafcommerce.core.payment.service.OrderPaymentService;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class OrderPaymentDTO extends BaseWrapper implements APIWrapper<OrderPayment>, APIUnwrapper<OrderPayment> {
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;

    @JsonProperty("orderId")
    protected Long orderId;

    @JsonProperty("billingAddress")
    protected OrderAddressDTO billingAddress;

    @JsonProperty("amount")
    protected Money amount;

    @JsonProperty("referenceNumber")
    protected String referenceNumber;

    @JsonProperty("paymentType")
    protected String paymentType;

    @JsonProperty("paymentGatewayType")
    protected String paymentGatewayType;

    @JsonProperty("transactions")
    protected List<PaymentTransactionDTO> transactions;
    
    @Override
    public OrderPayment unwrap(HttpServletRequest request, ApplicationContext context) {
        OrderPaymentService paymentService = (OrderPaymentService) context.getBean("blOrderPaymentService");
        OrderService orderService = (OrderService) context.getBean("blOrderService");
        OrderPayment payment = paymentService.create();
        payment.setId(this.id);
        if (this.orderId != null) {
            Order order = orderService.findOrderById(this.orderId);
            payment.setOrder(order);
        }
        if (this.billingAddress != null) {
            payment.setBillingAddress(this.billingAddress.unwrap(request, context));
        }
        payment.setAmount(this.amount);
        payment.setReferenceNumber(this.referenceNumber);
        if (this.paymentType != null) {
            payment.setType(PaymentType.getInstance(this.paymentType));
        }
        if (this.paymentGatewayType != null) {
            payment.setPaymentGatewayType(PaymentGatewayType.getInstance(this.paymentGatewayType));
        }
        List<PaymentTransaction> transactions = new ArrayList<>();
        for (PaymentTransactionDTO trans : ListUtils.emptyIfNull(this.transactions)) {
            PaymentTransaction newPaymentTransaction = trans.unwrap(request, context);
            newPaymentTransaction.setOrderPayment(payment);
            transactions.add(newPaymentTransaction);
        }
        payment.setTransactions(transactions);
        return payment;
    }

    @Override
    public void wrapDetails(OrderPayment payment, HttpServletRequest request) {
        this.id = payment.getId();
        if (payment.getOrder() != null) {
            this.orderId = payment.getOrder().getId();
        }
        if (payment.getBillingAddress() != null) {
            OrderAddressDTO billing = (OrderAddressDTO) context.getBean(OrderAddressDTO.class.getName());
            billing.wrapDetails(payment.getBillingAddress(), request);
            this.billingAddress = billing;
        }
        this.amount = payment.getAmount();
        this.referenceNumber = payment.getReferenceNumber();
        if (payment.getType() != null) {
            this.paymentType = payment.getType().getType();
        }
        if (payment.getGatewayType() != null) {
            this.paymentGatewayType = payment.getGatewayType().getType();
        }
        List<PaymentTransactionDTO> pts = new ArrayList<>();
        for (PaymentTransaction pt : ListUtils.emptyIfNull(payment.getTransactions())) {
            PaymentTransactionDTO transaction = (PaymentTransactionDTO) context.getBean(PaymentTransactionDTO.class.getName());
            transaction.wrapDetails(pt, request);
            pts.add(transaction);
        }
        this.transactions = pts;
    }

    @Override
    public void wrapSummary(OrderPayment payment, HttpServletRequest request) {
        wrapDetails(payment, request);
    }
}
