package com.broadleafcommerce.order.endpoint;

import org.broadleafcommerce.common.controller.FrameworkRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.broadleafcommerce.order.common.domain.OrderCustomer;
import com.broadleafcommerce.order.common.dto.OrderCustomerDTO;
import com.broadleafcommerce.order.common.service.OrderCustomerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@FrameworkRestController(@RequestMapping(path = "/customer"))
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OrderCustomerEndpoint {

    @Resource(name = "blOrderCustomerService")
    protected OrderCustomerService orderCustomerService;
    
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getCustomerById(HttpServletRequest request, @PathVariable Long customerId) {
        OrderCustomer customer = orderCustomerService.findOrderCustomerById(customerId);
        if (customer == null) {
            return new ResponseEntity("No customer exists with id " + customerId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customer, HttpStatus.OK);
    }
    
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity createCustomer(HttpServletRequest request, @RequestBody OrderCustomerDTO dto) {
        OrderCustomer customer = orderCustomerService.create();
        customer.setExternalId(dto.getExternalId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmailAddress(dto.getEmailAddress());
        return new ResponseEntity(orderCustomerService.saveOrderCustomer(customer), HttpStatus.OK);
    }
}
