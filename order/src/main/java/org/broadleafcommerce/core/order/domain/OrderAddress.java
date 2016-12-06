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
package org.broadleafcommerce.core.order.domain;

import org.broadleafcommerce.common.copy.MultiTenantCloneable;

/**
 * Created by brandon on 12/5/16.
 */
public interface OrderAddress extends MultiTenantCloneable<OrderAddress> {

    public Long getId();

    public void setId(Long id);

    public String getFullName();

    public void setFullName(String fullName);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String getCompanyName();

    public void setCompanyName(String companyName);

    public  String getAddressLine1();

    public void setAddressLine1(String addressLine1);

    public String getAddressLine2();

    public void setAddressLine2(String addressLine2);

    public String getAddressLine3();

    public void setAddressLine3(String addressLine3);

    public String getCityLocality();

    public void setCityLocality(String cityLocality);

    public String getStateProvinceRegion();

    public void setStateProvinceRegion(String stateRegion);

    public String getCountryCode();

    public void setCountryCode(String countryCode);

    public String getPostalCode();

    public void setPostalCode(String postalCode);

    public String getPhone();

    public void setPhone(String phone);

    public String getEmailAddress();

    public void setEmailAddress(String emailAddress);
}
