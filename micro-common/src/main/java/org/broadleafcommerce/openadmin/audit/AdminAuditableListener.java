/*
 * #%L
 * BroadleafCommerce Open Admin Platform
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
package org.broadleafcommerce.openadmin.audit;

import org.broadleafcommerce.common.time.SystemTime;
import org.broadleafcommerce.common.util.BLCFieldUtils;
import org.broadleafcommerce.common.web.BroadleafRequestContext;

import java.lang.reflect.Field;
import java.util.Calendar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AdminAuditableListener {

    @PrePersist
    public void setAuditCreationAndUpdateData(Object entity) throws Exception {
        setAuditCreationData(entity, new AdminAuditable());
        setAuditUpdateData(entity, new AdminAuditable());
    }

    @PreUpdate
    public void setAuditUpdateData(Object entity) throws Exception {
        setAuditUpdateData(entity, new AdminAuditable());
    }

    protected void setAuditValueAgent(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        try {
            BroadleafRequestContext context = BroadleafRequestContext.getBroadleafRequestContext();
            if (context != null && context.getAdmin() && context.getAdminUserId() != null) {
                field.setAccessible(true);
                field.set(entity, context.getAdminUserId());
            }
        } catch (IllegalStateException e) {
            //do nothing
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setAuditCreationData(Object entity, AdminAudit adminAudit) throws Exception {
        setAuditData(entity, adminAudit, "dateCreated", "createdBy");
    }

    protected void setAuditUpdateData(Object entity, AdminAudit adminAudit) throws Exception {
        setAuditData(entity, adminAudit, "dateUpdated", "updatedBy");
    }

    protected void setAuditData(Object entity, AdminAudit auditableObject, String dateField, String userField) throws Exception {
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
                Field agentField = auditable.getClass().getDeclaredField(userField);
                setAuditValueTemporal(temporalField, auditable);
                setAuditValueAgent(agentField, auditable);
            }
        }
    }

    protected void setAuditValueTemporal(Field field, Object entity) throws IllegalArgumentException, IllegalAccessException {
        Calendar cal = SystemTime.asCalendar();
        field.setAccessible(true);
        field.set(entity, cal.getTime());
    }

    protected String getAuditableFieldName() {
        return "auditable";
    }
}
