package com.broadleafcommerce.order.endpoint;

import org.broadleafcommerce.common.controller.FrameworkRestController;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.broadleafcommerce.order.common.domain.OrderCustomer;
import com.broadleafcommerce.order.common.service.OrderCustomerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@FrameworkRestController(@RequestMapping("/cart"))
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CartEndpoint {
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blOrderCustomerService")
    protected OrderCustomerService orderCustomerService;
    
    @RequestMapping(path = "/customer/{id}", method = RequestMethod.GET)
    public ResponseEntity findCartByCustomerId(HttpServletRequest request, @PathVariable Long id) {
        OrderCustomer customer = orderCustomerService.findOrderCustomerById(id);
        if (customer == null) {
            return new ResponseEntity("No customer with id " + id + " exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(orderService.findCartForCustomer(customer), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findCartById(HttpServletRequest request, @PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        if (order == null) {
            return new ResponseEntity("No order exists with id " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(order, HttpStatus.OK);
    }
    
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity createNewCartForCustomer(HttpServletRequest request, @RequestBody OrderCustomer customer) {
        if (customer == null) {
            return new ResponseEntity("No customer was sent", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(orderService.createNewCartForCustomer(customer), HttpStatus.OK);
    }
}
