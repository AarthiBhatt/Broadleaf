package org.broadleafcommerce.core.order.domain;

import org.broadleafcommerce.common.copy.CreateResponse;
import org.broadleafcommerce.common.copy.MultiTenantCopyContext;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.time.domain.TemporalTimestampListener;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by brandon on 12/5/16.
 */
@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_ADDRESS")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
public class OrderAddressImpl implements OrderAddress {

    @Id
    @GeneratedValue(generator = "OrderAddressId")
    @GenericGenerator(
            name="OrderAddressId",
            strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name="segment_value", value="OrderAddressImpl"),
                    @Parameter(name="entity_name", value="org.broadleafcommerce.core.order.domain.OrderAddressImpl")
            }
    )
    @Column(name = "ORDER_ADDRESS_ID")
    protected Long id;

    @Column(name = "FULL_NAME")
    protected String fullName;

    @Column(name = "FIRST_NAME")
    protected String firstName;

    @Column(name = "LAST_NAME")
    protected String lastName;

    @Column(name = "COMPANY_NAME")
    protected String companyName;

    @Column(name = "ADDRESS_LINE1", nullable = false)
    protected String addressLine1;

    @Column(name = "ADDRESS_LINE2")
    protected String addressLine2;

    @Column(name = "ADDRESS_LINE3")
    protected String addressLine3;

    @Column(name = "CITY_LOCALITY", nullable = false)
    protected String cityLocality;

    @Column(name = "STATE_PROV_REGION")
    protected String stateProvinceRegion;

    @Column(name = "COUNTRY_CODE")
    protected String countryCode;

    @Column(name = "POSTAL_CODE")
    protected String postalCode;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "EMAIL_ADDRESS")
    protected String emailAddress;

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getAddressLine1() {
        return addressLine1;
    }

    @Override
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Override
    public String getAddressLine2() {
        return addressLine2;
    }

    @Override
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Override
    public String getAddressLine3() {
        return addressLine3;
    }

    @Override
    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    @Override
    public String getCityLocality() {
        return cityLocality;
    }

    @Override
    public void setCityLocality(String cityLocality) {
        this.cityLocality = cityLocality;
    }

    @Override
    public String getStateProvinceRegion() {
        return stateProvinceRegion;
    }

    @Override
    public void setStateProvinceRegion(String stateProvinceRegion) {
        this.stateProvinceRegion = stateProvinceRegion;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public <G extends OrderAddress> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OrderAddress cloned = createResponse.getClone();
        cloned.setFullName(fullName);
        cloned.setFirstName(firstName);
        cloned.setLastName(lastName);
        cloned.setAddressLine1(addressLine1);
        cloned.setAddressLine2(addressLine2);
        cloned.setAddressLine3(addressLine3);
        cloned.setCityLocality(cityLocality);
        cloned.setCountryCode(countryCode);
        cloned.setStateProvinceRegion(stateProvinceRegion);
        cloned.setPostalCode(postalCode);
        cloned.setEmailAddress(emailAddress);
        cloned.setPhone(phone);
        cloned.setCompanyName(companyName);
        return createResponse;
    }
}
