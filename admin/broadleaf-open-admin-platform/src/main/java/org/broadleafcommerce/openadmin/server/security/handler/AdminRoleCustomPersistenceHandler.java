/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2017 Broadleaf Commerce
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
package org.broadleafcommerce.openadmin.server.security.handler;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FilterAndSortCriteria;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityAggregator;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityRetrivalService;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.broadleafcommerce.openadmin.server.service.handler.ClassCustomPersistenceHandlerAdapter;
import org.broadleafcommerce.openadmin.server.service.persistence.module.PersistenceModule;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component("blAdminRoleCustomPersistenceHandler")
public class AdminRoleCustomPersistenceHandler extends ClassCustomPersistenceHandlerAdapter {

    @Resource(name = "blAdminSecurityAggregator")
    protected AdminSecurityAggregator securityAggregator;
    
    @Resource(name = "blAdminSecurityRetrivalService")
    protected AdminSecurityRetrivalService retrivalService;
    
    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService securityService;
    
    public AdminRoleCustomPersistenceHandler() {
        super(AdminRole.class);
    }
    
    @Override
    public Boolean canHandleFetch(PersistencePackage persistencePackage) {
        return classMatches(persistencePackage);
    }
    
    @Override
    public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        PersistenceModule myModule = helper.getCompatibleModule(persistencePackage.getPersistencePerspective().getOperationTypes().getFetchType());
        DynamicResultSet results = myModule.fetch(persistencePackage, cto);
        List<Long> filterRoles = null;
        FilterAndSortCriteria userCriteria = cto.getCriteriaMap().get("allUsers");
        FilterAndSortCriteria idCriteria = cto.getCriteriaMap().get("id");
        List<Long> specificIds = null;
        if (idCriteria != null) {
            specificIds = new ArrayList<>();
            for (String strId : idCriteria.getFilterValues()) {
                specificIds.add(Long.parseLong(strId));
            }
        }
        if (userCriteria != null) {
            for (String id : userCriteria.getFilterValues()) {
                AdminUser user = securityService.readAdminUserById(Long.parseLong(id));
                List<AdminRole> roles = retrivalService.findAllRolesForAdminUser(user);
                if (roles != null) {
                    if (filterRoles == null) {
                        filterRoles = new ArrayList<>();
                    }
                }
                for (AdminRole role : roles) {
                    filterRoles.add(role.getId());
                }
            }
        }
        List<AdminRole> configuredRoles = securityAggregator.getAllAdminRoles();
        Map<Long, AdminRole> configuredRoleMap = new HashMap<>();
        for (AdminRole role : configuredRoles) {
            if (role.getId() != null && 
                (filterRoles == null || filterRoles.contains(role.getId())) &&
                (specificIds == null || specificIds.contains(role.getId()))) {
                configuredRoleMap.put(role.getId(), role);
            }
        }
        for (Entity entity : results.getRecords()) {
            Property idProp = entity.findProperty("id");
            Long id = Long.parseLong(idProp.getValue());
            configuredRoleMap.remove(id);
        }
        List<Entity> allEntities = new ArrayList<>(Arrays.asList(results.getRecords()));
        for (AdminRole configured : configuredRoleMap.values()) {
            Entity entity = helper.getRecord(configured.getClass(), persistencePackage.getPersistencePerspective(), configured);
            allEntities.add(entity);
        }
        results.setRecords(allEntities.toArray(new Entity[allEntities.size()]));
        results.setTotalRecords(allEntities.size());
        return results;
    }
    
}
