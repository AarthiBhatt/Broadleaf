package com.broadleafcommerce.order.common.dto;

import org.apache.commons.collections4.ListUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Long id;
    public String name;
    public OrderCustomerDTO orderCustomer;
    public String status;
    public Money totalTax;
    public Money totalFulfillmentCharges;
    public Money subTotal;
    public Money total;
    public Date submitDate;
    public String orderNumber;
    public String emailAddress;
    public List<OrderItemDTO> orderItems;
    public List<OfferCodeDTO> offerCodes;
    
    public OrderDTO(@NonNull  Order order) {
        this.id = order.getId();
        this.name = order.getName();
        if (order.getOrderCustomer() != null) {
            this.orderCustomer = new OrderCustomerDTO(order.getOrderCustomer());
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
            ois.add(new OrderItemDTO(oi));
        }
        this.orderItems = ois;
        List<OfferCodeDTO> ocs = new ArrayList<>();
        for (OfferCode oc : ListUtils.emptyIfNull(order.getAddedOfferCodes())) {
            ocs.add(new OfferCodeDTO(oc));
        }
    }
}
