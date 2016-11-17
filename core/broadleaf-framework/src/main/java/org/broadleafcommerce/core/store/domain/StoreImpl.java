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
package org.broadleafcommerce.core.store.domain;

import org.broadleafcommerce.common.persistence.ArchiveStatus;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BLC_STORE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@SQLDelete(sql = "UPDATE BLC_STORE SET ARCHIVED = 'Y' WHERE STORE_ID = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@AdminPresentationMergeOverrides(
        {
                @AdminPresentationMergeOverride(name = "address", mergeEntries =
                @AdminPresentationMergeEntry(propertyType = "tab", overrideValue = "StoreImpl_Store_Location")),
                @AdminPresentationMergeOverride(name = "address.firstName", mergeEntries =
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED, booleanOverrideValue = true)),
                @AdminPresentationMergeOverride(name = "address.lastName", mergeEntries =
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED, booleanOverrideValue = true)),
                @AdminPresentationMergeOverride(name = "address.fullName", mergeEntries =
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED, booleanOverrideValue = true)),
                @AdminPresentationMergeOverride(name = "address.emailAddress", mergeEntries =
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED, booleanOverrideValue = true)),
                @AdminPresentationMergeOverride(name = "address.companyName", mergeEntries =
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED, booleanOverrideValue = true)),
                @AdminPresentationMergeOverride(name = "address.addressLine1", mergeEntries =
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.PROMINENT, booleanOverrideValue = true))
        }
)
public class StoreImpl implements Store, StoreAdminPresentation {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "StoreId")
    @GenericGenerator(
            name = "StoreId",
            strategy = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name = "segment_value", value = "StoreImpl"),
                    @Parameter(name = "entity_name", value = "org.broadleafcommerce.core.store.domain.StoreImpl")
            }
    )
    @Column(name = "STORE_ID", nullable = false)
    @AdminPresentation(friendlyName = "StoreImpl_Store_ID", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    @Column(name = "STORE_NAME", nullable = false)
    @AdminPresentation(friendlyName = "StoreImpl_Store_Name",
            prominent = true, gridOrder = 1, columnWidth = "200px",
            requiredOverride = RequiredOverride.REQUIRED)
    protected String name;

    @Column(name = "STORE_NUMBER")
    @AdminPresentation(friendlyName = "StoreImpl_Store_Number")
    protected String storeNumber;

    @Column(name = "OPEN")
    @AdminPresentation(friendlyName = "StoreImpl_Open")
    protected Boolean open;

    @Column(name = "STORE_HOURS")
    @AdminPresentation(friendlyName = "StoreImpl_Store_Hours", fieldType = SupportedFieldType.HTML)
    protected String storeHours;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = AddressImpl.class)
    @JoinColumn(name = "ADDRESS_ID")
    protected Address address;

    @Column(name = "LATITUDE")
    @AdminPresentation(friendlyName = "StoreImpl_lat", order = FieldOrder.LATITUDE,
            tab = TabName.Location,
            group = GroupName.Geocoding, columnWidth = "200px")
    protected Double latitude;

    @Column(name = "LONGITUDE")
    @AdminPresentation(friendlyName = "StoreImpl_lng", order = FieldOrder.LONGITUDE,
            tab = TabName.Location,
            group = GroupName.Geocoding,
            gridOrder = 10, columnWidth = "200px")
    protected Double longitude;

    @Embedded
    protected ArchiveStatus archiveStatus = new ArchiveStatus();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String getStoreNumber() {
        return storeNumber;
    }

    @Override
    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    @Override
    public Boolean getOpen() {
        return open;
    }

    @Override
    public void setOpen(Boolean open) {
        this.open = open;
    }

    @Override
    public String getStoreHours() {
        return storeHours;
    }

    @Override
    public void setStoreHours(String storeHours) {
        this.storeHours = storeHours;
    }

    @Override
    public Character getArchived() {
        ArchiveStatus temp;
        if (archiveStatus == null) {
            temp = new ArchiveStatus();
        } else {
            temp = archiveStatus;
        }
        return temp.getArchived();
    }

    @Override
    public void setArchived(Character archived) {
        if (archiveStatus == null) {
            archiveStatus = new ArchiveStatus();
        }
        archiveStatus.setArchived(archived);
    }

    @Override
    public boolean isActive() {
        return 'Y' != getArchived();
    }


}
