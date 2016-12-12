package com.broadleafcommerce.order.common.dto;

import org.apache.commons.collections4.ListUtils;
import org.broadleafcommerce.common.api.APIWrapper;
import org.broadleafcommerce.common.api.BaseWrapper;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.offer.domain.OfferCode;
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
    }

    @Override
    public void wrapSummary(Order order, HttpServletRequest request) {
        wrapDetails(order, request);
    }
}
