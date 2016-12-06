package org.broadleafcommerce.core.web.translation;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.broadleafcommerce.profile.core.service.PhoneService;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.order.common.domain.OrderAddress;
import com.broadleafcommerce.order.common.service.OrderAddressService;

import javax.annotation.Resource;

@Service("blOrderAddressTranslationService")
public class OrderAddressTranslationServiceImpl implements OrderAddressTranslationService {

    @Resource(name = "blAddressService")
    protected AddressService addressService;
    
    @Resource(name = "blOrderAddressService")
    protected OrderAddressService orderAddressService;
    
    @Resource(name = "blPhoneService")
    protected PhoneService phoneService;
    
    @Override
    public Address convertOrderAddressToAddress(OrderAddress orderAddress) {
        Address addy = addressService.create();
        addy.setAddressLine1(orderAddress.getAddressLine1());
        addy.setAddressLine2(orderAddress.getAddressLine2());
        addy.setAddressLine3(orderAddress.getAddressLine3());
        addy.setCity(orderAddress.getCityLocality());
        addy.setCompanyName(orderAddress.getCompanyName());
        addy.setIsoCountrySubdivision(orderAddress.getCountryCode());
        addy.setEmailAddress(orderAddress.getEmailAddress());
        addy.setFirstName(orderAddress.getFirstName());
        addy.setLastName(orderAddress.getLastName());
        addy.setFullName(orderAddress.getFullName());
        addy.setPostalCode(orderAddress.getPostalCode());
        addy.setStateProvinceRegion(orderAddress.getStateProvinceRegion());
        
        Phone addyPhone = phoneService.create();
        addyPhone.setPhoneNumber(orderAddress.getPhone());
        addy.setPhonePrimary(addyPhone);
        
        return addy;
    }

    @Override
    public OrderAddress convertAddressToOrderAddress(Address address) {
        OrderAddress orderAddress = orderAddressService.create();
        orderAddress.setAddressLine1(address.getAddressLine1());
        orderAddress.setAddressLine2(address.getAddressLine2());
        orderAddress.setAddressLine3(address.getAddressLine3());
        orderAddress.setCityLocality(address.getCity());
        orderAddress.setCompanyName(address.getCompanyName());
        orderAddress.setCountryCode(address.getIsoCountrySubdivision());
        orderAddress.setEmailAddress(address.getEmailAddress());
        orderAddress.setFirstName(address.getFirstName());
        orderAddress.setLastName(address.getLastName());
        orderAddress.setFullName(address.getFullName());
        orderAddress.setPhone(address.getPhonePrimary().getPhoneNumber());
        orderAddress.setPostalCode(address.getPostalCode());
        orderAddress.setStateProvinceRegion(address.getStateProvinceRegion());
        return orderAddress;
    }
    
}
