/*
 * #%L
 * BroadleafCommerce Framework
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
package com.broadleafcommerce.order.common.domain;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.core.order.domain.OrderCustomerAdminPresentation.FieldOrder;
import org.broadleafcommerce.core.order.domain.OrderCustomerAdminPresentation.GroupName;
import org.broadleafcommerce.core.order.domain.OrderCustomerAdminPresentation.TabName;
import org.broadleafcommerce.core.payment.domain.CustomerPayment;
import org.broadleafcommerce.core.payment.domain.CustomerPaymentImpl;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_CUSTOMER")
public class OrderCustomerImpl implements OrderCustomer {

    @Id
    @GeneratedValue(generator = "OrderCustomerId")
    @GenericGenerator(
            name = "OrderCustomerId",
            strategy = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name = "segment_value", value = "OrderCustomerImpl"),
                    @Parameter(name = "entity_name", value = "com.broadleafcommerce.order.common.domain.OrderCustomerImpl")
            }
    )
    @Column(name = "CUSTOMER_ID")
    @AdminPresentation(friendlyName = "CustomerImpl_Customer_Id", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;
    
    @Column(name = "EXTERNAL_ID")
    @AdminPresentation(friendlyName = "CustomerImpl_External_Id",
            group = GroupName.Customer, order = FieldOrder.EXTERNAL_ID)
    protected Long externalId;
    
    @Column(name = "FIRST_NAME")
    @AdminPresentation(friendlyName = "CustomerImpl_First_Name",
            group = GroupName.Customer, order = FieldOrder.FIRST_NAME,
            prominent = true, gridOrder = 2000)
    protected String firstName;

    @Column(name = "LAST_NAME")
    @AdminPresentation(friendlyName = "CustomerImpl_Last_Name",
            group = GroupName.Customer, order = FieldOrder.LAST_NAME,
            prominent = true, gridOrder = 3000)
    protected String lastName;
    
    @Column(name = "EMAIL_ADDRESS")
    @Index(name="ORDER_EMAIL_INDEX", columnNames={"EMAIL_ADDRESS"})
    @AdminPresentation(friendlyName = "OrderImpl_Order_Email_Address", group = GroupName.Customer,
            order=FieldOrder.EMAIL)
    protected String emailAddress;
    
    @OneToMany(mappedBy = "customer", targetEntity = CustomerPaymentImpl.class, cascade = { CascadeType.ALL })
    @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
    @BatchSize(size = 50)
    @AdminPresentationCollection(friendlyName = "OrderCustomerImpl_Customer_Payments",
            tab = TabName.PaymentMethods, order = 1000,
            addType = AddMethodType.PERSIST,
            readOnly = true)
    protected List<CustomerPayment> customerPayments = new ArrayList<>();

    @Column(name = "IS_TAX_EXEMPT")
    @AdminPresentation(friendlyName = "OrderImpl_Order_Is_Tax_Exempt", group = GroupName.Customer)
    protected Boolean isTaxExempt = false;

    @Column(name = "TAX_EXEMPTION_CODE")
    @AdminPresentation(friendlyName = "OrderImpl_Order_TaxExemptCode", group = GroupName.Customer)
    protected String taxExemptionCode;

    @Lob
    @Column(name = "CUSTOMER_ATTRIBUTES_JSON", length = Integer.MAX_VALUE - 1)
    protected String customerAttributesJson;

    @Transient
    protected Map<String, Object> customerAttributesMap;

    @Transient
    protected boolean anonymous;
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public Long getExternalId() {
        return externalId;
    }
    
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }
    
    @Override
    public List<CustomerPayment> getCustomerPayments() {
        return customerPayments;
    }
    
    @Override
    public boolean isAnonymous() {
        return anonymous;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }
    
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    @Override
    public void setCustomerPayments(List<CustomerPayment> customerPayments) { this.customerPayments = customerPayments; }

    @Override
    public Boolean getTaxExempt() { return isTaxExempt; }

    @Override
    public void setTaxExempt(Boolean taxExempt) { isTaxExempt = taxExempt; }

    @Override
    public String getTaxExemptionCode() { return taxExemptionCode; }

    @Override
    public void setTaxExemptionCode(String taxExemptionCode) { this.taxExemptionCode = taxExemptionCode; }

    @Override
    public String getCustomerAttributesJson() { return customerAttributesJson; }

    @Override
    public void setCustomerAttributesJson(String customerAttributesJson) {
        this.customerAttributesJson = customerAttributesJson;
        this.customerAttributesMap = null;
    }

    @Override
    public Map<String, Object> getCustomerAttributesMap() throws IOException {
        if (customerAttributesMap == null) {
            ObjectMapper mapper = new ObjectMapper();
            customerAttributesMap = mapper.readValue(customerAttributesJson, new TypeReference<Map<String, Object>>() {});
        }

        return customerAttributesMap;
    }

    @Override
    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

}
