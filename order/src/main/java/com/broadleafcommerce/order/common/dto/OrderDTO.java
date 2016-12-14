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
import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.payment.domain.OrderPayment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;

@Data
public class OrderDTO extends BaseWrapper implements APIWrapper<Order> {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    protected Long id;
    
    @JsonProperty("name")
    protected String name;
    
    @JsonProperty("orderCustomer")
    protected OrderCustomerDTO orderCustomer;
    
    @JsonProperty("status")
    protected String status;
    
    @JsonProperty("totalTax")
    protected Money totalTax;
    
    @JsonProperty("totalFulfillmentCharges")
    protected Money totalFulfillmentCharges;
    
    @JsonProperty("subTotal")
    protected Money subTotal;
    
    @JsonProperty("total")
    protected Money total;
    
    @JsonProperty("submitDate")
    protected Date submitDate;
    
    @JsonProperty("orderNumber")
    protected String orderNumber;
    
    @JsonProperty("emailAddress")
    protected String emailAddress;
    
    @JsonProperty("orderItems")
    protected List<OrderItemDTO> orderItems;
    
    @JsonProperty("offerCodes")
    protected List<OfferCodeDTO> offerCodes;
    
    @JsonProperty("orderPayments")
    protected List<OrderPaymentDTO> orderPayments;
    
    @JsonProperty("fulfillmentGroups")
    protected List<FulfillmentGroupDTO> fulfillmentGroups;
    
    @Override
    public void wrapDetails(Order order, HttpServletRequest request) {
        this.id = order.getId();
        this.name = order.getName();
        if (order.getOrderCustomer() != null) {
            OrderCustomerDTO customer = (OrderCustomerDTO) context.getBean(OrderCustomerDTO.class.getName());
            customer.wrapDetails(order.getOrderCustomer(), request);
            this.orderCustomer = customer;
        }
        if (order.getStatus() != null) {
            this.status = order.getStatus().getType(); 
        }
        this.totalTax = order.getTotalTax();
        this.totalFulfillmentCharges = order.getTotalFulfillmentCharges();
        this.subTotal = order.getSubTotal();
        this.total = order.getTotal();
        this.submitDate = order.getSubmitDate();
        this.orderNumber = order.getOrderNumber();
        this.emailAddress = order.getEmailAddress();
        List<OrderItemDTO> ois = new ArrayList<>();
        for (OrderItem oi : ListUtils.emptyIfNull(order.getOrderItems())) {
            OrderItemDTO orderItem = (OrderItemDTO) context.getBean(OrderItemDTO.class.getName());
            orderItem.wrapDetails(oi, request);
            ois.add(orderItem);
        }
        this.orderItems = ois;
        List<OfferCodeDTO> ocs = new ArrayList<>();
        for (OfferCode oc : ListUtils.emptyIfNull(order.getAddedOfferCodes())) {
            OfferCodeDTO offerCode = (OfferCodeDTO) context.getBean(OfferCodeDTO.class.getName());
            offerCode.wrapDetails(oc, request);
            ocs.add(offerCode);
        }
        this.offerCodes = ocs;
        List<OrderPaymentDTO> ops = new ArrayList<>();
        for (OrderPayment op : ListUtils.emptyIfNull(order.getPayments())) {
            OrderPaymentDTO orderPayment = (OrderPaymentDTO) context.getBean(OrderPaymentDTO.class.getName());
            orderPayment.wrapDetails(op, request);
            ops.add(orderPayment);
        }
        this.orderPayments = ops;
        List<FulfillmentGroupDTO> fgs = new ArrayList<>();
        for (FulfillmentGroup fg : ListUtils.emptyIfNull(order.getFulfillmentGroups())) {
            FulfillmentGroupDTO fgDto = (FulfillmentGroupDTO) context.getBean(FulfillmentGroupDTO.class.getName());
            fgDto.wrapDetails(fg, request);
            fgs.add(fgDto);
        }
        this.fulfillmentGroups = fgs;
    }

    @Override
    public void wrapSummary(Order order, HttpServletRequest request) {
        wrapDetails(order, request);
    }
}
