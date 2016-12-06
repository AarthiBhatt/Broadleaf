package org.broadleafcommerce.core.web.translation;

import org.broadleafcommerce.core.order.domain.OrderAddress;
import org.broadleafcommerce.profile.core.domain.Address;

public interface OrderAddressTranslationService {
    public Address convertOrderAddressToAddress(OrderAddress orderAddress);
    public OrderAddress convertAddressToOrderAddress(Address address);
}
