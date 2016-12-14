/*
 * #%L
 * BroadleafCommerce Common Libraries
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
package org.broadleafcommerce.common.audit;

import org.broadleafcommerce.common.time.SystemTime;
import org.broadleafcommerce.common.util.BLCFieldUtils;

import java.lang.reflect.Field;
import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Implements behavior shared by auditable listener implementations
 *
 * @author Chris Kittrell (ckittrell)
 */
public class AuditableListener {

    /**
     * Method that will be invoked in a registered listener to set the entity's creation data.
     *  In most cases, calling {@link AuditableListener#setAuditCreationData(Object, Auditable)} should suffice.
     *
     * @param entity
     * @return
     */
    @PrePersist
    public void setAuditCreationAndUpdateData(Object entity) throws Exception {
        setAuditCreationData(entity, new Auditable());
        setAuditUpdateData(entity, new Auditable());
    }

    /**
     * Method that will be invoked in a registered listener to set the entity's update data.
     *  In most cases, calling {@link AuditableListener#setAuditUpdateData(Object, Auditable)} should suffice.
     *
     * @param entity
     * @return
     */
    @PreUpdate
    public void setAuditUpdateData(Object entity) throws Exception {
        setAuditUpdateData(entity, new Auditable());
    }

    /**
     * Sets the value of the dateCreated, createdBy, and dateUpdated fields.
     *
     * @param entity
     * @param auditable
     * @return
     */
    protected void setAuditCreationData(Object entity, Auditable auditable) throws Exception {
        setAuditData(entity, auditable, "dateCreated");
    }

    /**
     * Sets the value of the dateUpdated and updatedBy fields.
     *
     * @param entity
     * @param auditable
     * @return
     */
    protected void setAuditUpdateData(Object entity, Auditable auditable) throws Exception {
        setAuditData(entity, auditable, "dateUpdated");
    }

    protected void setAuditData(Object entity, Auditable auditableObject, String dateField) throws Exception {
        if (entity.getClass().isAnnotationPresent(Entity.class)) {
            Field field = BLCFieldUtils.getSingleField(entity.getClass(), getAuditableFieldName());
            field.setAccessible(true);
            if (field.isAnnotationPresent(Embedded.class)) {
                Object auditable = field.get(entity);
                if (auditable == null) {
                    field.set(entity, auditableObject);
                    auditable = field.get(entity);
                }
                Field temporalField = auditable.getClass().getDeclaredField(dateField);
                setAuditValueTemporal(temporalField, auditable);
            }
        }
    }

    /**
     * Used to set the timestamp for dateCreated and dateUpdated.
     *
     * @param field
     * @param entity
     * @return
     */
    protected void setAuditValueTemporal(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        Calendar cal = SystemTime.asCalendar();
        field.setAccessible(true);
        field.set(entity, cal.getTime());
    }

    /**
     * Gathers the auditable field name.
     *  The major purpose of this method is to provide a hook point for extensions to declare a different field name.
     *
     * @return the name of the auditable field
     */
    protected String getAuditableFieldName() {
        return "auditable";
    }
    
}
