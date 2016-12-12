package com.broadleafcommerce.order.endpoint;

import org.broadleafcommerce.common.controller.FrameworkRestController;
import org.broadleafcommerce.core.checkout.service.CheckoutService;
import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.broadleafcommerce.order.common.dto.OrderDTO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@FrameworkRestController(@RequestMapping(path = "/checkout"))
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CheckoutEndpoint {

    @Resource(name = "blCheckoutService")
    protected CheckoutService checkoutService;
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @RequestMapping(path = "/{orderId}", method = RequestMethod.POST)
    public ResponseEntity checkout(HttpServletRequest request, @PathVariable Long orderId) {
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity("Order with id " + orderId + " exists", HttpStatus.BAD_REQUEST);
        }
        
        try {
            CheckoutResponse response = checkoutService.performCheckout(order);
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.wrapDetails(response.getOrder(), request);
            return new ResponseEntity(orderDTO, HttpStatus.OK);
        } catch (CheckoutException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
