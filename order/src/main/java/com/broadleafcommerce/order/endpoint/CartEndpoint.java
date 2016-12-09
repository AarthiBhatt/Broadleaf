package com.broadleafcommerce.order.endpoint;

import org.broadleafcommerce.common.controller.FrameworkRestController;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.service.OfferService;
import org.broadleafcommerce.core.offer.service.exception.OfferException;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
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
    
    @Resource(name = "blOfferService")
    protected OfferService offerService;

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
    
    @RequestMapping(path = "/{id}/add", method = RequestMethod.POST)
    public ResponseEntity addItemToOrder(HttpServletRequest request, @PathVariable Long id, @RequestBody OrderItemRequestDTO dto) {
        try {
            return new ResponseEntity(orderService.addItem(id, dto, true), HttpStatus.OK);
        } catch (AddToCartException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(path = "/{orderId}/remove/{itemId}", method = RequestMethod.POST)
    public ResponseEntity removeItemFromOrder(HttpServletRequest request, @PathVariable("orderId") Long orderId, @PathVariable("itemId") Long orderItemId) {
        try {
            return new ResponseEntity(orderService.removeItem(orderId, orderItemId, true), HttpStatus.OK);
        } catch (RemoveFromCartException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(path = "/{orderId}/update", method = RequestMethod.POST)
    public ResponseEntity updateItemOnOrder(HttpServletRequest request, @PathVariable("orderId") Long orderId, @RequestBody OrderItemRequestDTO dto) {
        try {
            return new ResponseEntity(orderService.updateItemQuantity(orderId, dto, true), HttpStatus.OK);
        } catch (UpdateCartException | RemoveFromCartException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(path = "/{orderId}/add/promo/{promoCode}", method = RequestMethod.POST)
    public ResponseEntity addPromoToOrder(HttpServletRequest request, @PathVariable("orderId") Long orderId, @PathVariable("promoCode") String promoCode) {
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity("No order exists for id " + orderId, HttpStatus.BAD_REQUEST);
        }
        OfferCode offerCode = offerService.lookupOfferCodeByCode(promoCode);
        if (offerCode == null) {
            return new ResponseEntity("No offerCode exists for offer code " + promoCode, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity(orderService.addOfferCode(order, offerCode, true), HttpStatus.OK);
        } catch (OfferException | PricingException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(path = "/{orderId}/remove/promo/{promoCode}", method = RequestMethod.POST)
    public ResponseEntity removePromoFromOrder(HttpServletRequest request, @PathVariable("orderId") Long orderId, @PathVariable("promoCode") String promoCode) {
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            return new ResponseEntity("No order exists for id " + orderId, HttpStatus.BAD_REQUEST);
        }
        OfferCode offerCode = offerService.lookupOfferCodeByCode(promoCode);
        if (offerCode == null) {
            return new ResponseEntity("No offerCode exists for offer code " + promoCode, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity(orderService.removeOfferCode(order, offerCode, true), HttpStatus.OK);
        } catch (PricingException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
