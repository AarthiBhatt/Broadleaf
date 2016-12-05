package org.broadleafcommerce.core.order.domain;

import org.broadleafcommerce.common.copy.MultiTenantCloneable;

/**
 * Created by brandon on 12/5/16.
 */
public interface OrderAddress extends MultiTenantCloneable<OrderAddress> {

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
