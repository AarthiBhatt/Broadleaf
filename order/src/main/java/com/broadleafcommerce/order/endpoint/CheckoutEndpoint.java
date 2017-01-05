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
package com.broadleafcommerce.order.endpoint;

import org.broadleafcommerce.common.api.BaseEndpoint;
import org.broadleafcommerce.common.controller.FrameworkMapping;
import org.broadleafcommerce.common.controller.FrameworkRestController;
import org.broadleafcommerce.core.checkout.service.CheckoutService;
import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import com.broadleafcommerce.order.common.dto.OrderDTO;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@FrameworkRestController
@FrameworkMapping("/checkout")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CheckoutEndpoint extends BaseEndpoint {

    @Resource(name = "blCheckoutService")
    protected CheckoutService checkoutService;

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @FrameworkMapping(path = "/{orderId}", method = RequestMethod.POST)
    public ResponseEntity checkout(HttpServletRequest request, @PathVariable Long orderId) {
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity("Order with id " + orderId + " exists", HttpStatus.BAD_REQUEST);
        }
        
        try {
            CheckoutResponse response = checkoutService.performCheckout(order);
            OrderDTO orderDTO = (OrderDTO) context.getBean(OrderDTO.class.getName());
            orderDTO.wrapDetails(response.getOrder(), request);
            return new ResponseEntity(orderDTO, HttpStatus.OK);
        } catch (CheckoutException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
