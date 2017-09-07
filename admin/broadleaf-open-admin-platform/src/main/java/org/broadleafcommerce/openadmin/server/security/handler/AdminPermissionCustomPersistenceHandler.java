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

package org.broadleafcommerce.openadmin.server.security.handler;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.FilterAndSortCriteria;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityAggregator;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityRetrivalService;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;
import org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.broadleafcommerce.openadmin.server.service.persistence.module.PersistenceModule;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author Jeff Fischer
 */
@Component("blAdminPermissionCustomPersistenceHandler")
public class AdminPermissionCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(AdminPermissionCustomPersistenceHandler.class);

    @Resource(name = "blAdminSecurityAggregator")
    protected AdminSecurityAggregator securityAggregator;
    
    @Resource(name = "blAdminSecurityRetrivalService")
    protected AdminSecurityRetrivalService retrivalService;
    
    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService securityService;
    
    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        String[] criteria = persistencePackage.getCustomCriteria();
        return !ArrayUtils.isEmpty(criteria) && criteria[0].equals("createNewPermission") && AdminPermission.class.getName().equals(ceilingEntityFullyQualifiedClassname);
    }

    @Override
    public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
        return canHandleAdd(persistencePackage);
    }

    @Override
    public Boolean canHandleFetch(PersistencePackage persistencePackage) {
        String ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        return AdminPermissionImpl.class.getName().equals(ceilingEntityFullyQualifiedClassname);

    }

    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        if (persistencePackage.getEntity().findProperty("id") != null && !StringUtils.isEmpty(persistencePackage.getEntity().findProperty("id").getValue())) {
            return update(persistencePackage, dynamicEntityDao, helper);
        }
        Entity entity = checkPermissionName(persistencePackage);
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            AdminPermission adminInstance = (AdminPermission) Class.forName(entity.getType()[0]).newInstance();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(AdminPermission.class.getName(), persistencePerspective);
            adminInstance = (AdminPermission) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

            adminInstance = dynamicEntityDao.merge(adminInstance);

            Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

            return adminEntity;
        } catch (Exception e) {
            throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
        }
    }

    protected Entity checkPermissionName(PersistencePackage persistencePackage) throws ServiceException {
        Entity entity = persistencePackage.getEntity();
        Property prop = entity.findProperty("name");
        String name = prop.getValue();
        name = name.toUpperCase();
        if (!name.startsWith("PERMISSION_")) {
            throw new ServiceException("All Permission names must start with PERMISSION_");
        }
        String[] parts = name.split("_");
        if (parts.length < 3) {
            throw new ServiceException("All Permission names must adhere to the naming standard: PERMISSION_[Permission Type]_[User Defined Section]. E.g. PERMISSION_READ_CATEGORY");
        }
        if (PermissionType.getInstance(parts[1]) == null) {
            throw new ServiceException("All Permission names must specify a valid permission type as part of the name. The permission name you specified (" + name + ") denotes the permission type of (" + parts[1] + "), which is not valid. See org.broadleafcommerce.openadmin.server.security.service.type.PermissionType for valid permission types.");
        }
        prop.setValue(name);
        return entity;
    }

    @Override
    public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity = checkPermissionName(persistencePackage);
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(AdminPermission.class.getName(), persistencePerspective);
            Object primaryKey = helper.getPrimaryKey(entity, adminProperties);
            AdminPermission adminInstance = (AdminPermission) dynamicEntityDao.retrieve(Class.forName(entity.getType()[0]), primaryKey);
            adminInstance = (AdminPermission) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

            adminInstance = dynamicEntityDao.merge(adminInstance);

            Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

            return adminEntity;
        } catch (Exception e) {
            throw new ServiceException("Unable to update entity for " + entity.getType()[0], e);
        }
    }

    @Override
    public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        addFriendlyRestriction(cto);
        addDefaultSort(cto);

        PersistenceModule myModule = helper.getCompatibleModule(persistencePackage.getPersistencePerspective().getOperationTypes().getFetchType());
        DynamicResultSet results = myModule.fetch(persistencePackage, cto);
        List<AdminPermission> configuredPermissions = securityAggregator.getAllFriendlyPermissions();
        Map<Long, AdminPermission> configuredPermissionIdMap = new HashMap<>();
        List<Long> roleFilterPermissions = filterPermissionsForRole(cto);
        List<Long> userFilterPermissions = filterPermissionsForUser(cto);
        List<Long> filterPermissions = null;
        if (roleFilterPermissions != null || userFilterPermissions != null) {
            filterPermissions = new ArrayList<>();
        }
        if (roleFilterPermissions != null) {
            filterPermissions.addAll(roleFilterPermissions);
        }
        if (userFilterPermissions != null) {
            filterPermissions.addAll(userFilterPermissions);
        }
        
        for (AdminPermission perm : configuredPermissions) {
            if (perm.getId() != null && (filterPermissions == null || filterPermissions.contains(perm.getId()))) {
                configuredPermissionIdMap.put(perm.getId(), perm);
            }
        }
        for (Entity entity : results.getRecords()) {
            Property idProp = entity.findProperty("id");
            Long id = Long.parseLong(idProp.getValue());
            configuredPermissionIdMap.remove(id);
        }
        List<Entity> allEntities = new ArrayList<>(Arrays.asList(results.getRecords()));
        for (AdminPermission configured : configuredPermissionIdMap.values()) {
            Entity entity = helper.getRecord(configured.getClass(), persistencePackage.getPersistencePerspective(), configured);
            allEntities.add(entity);
        }
        results.setRecords(allEntities.toArray(new Entity[allEntities.size()]));
        results.setTotalRecords(allEntities.size());
        return results;
    }

    protected List<Long> filterPermissionsForRole(CriteriaTransferObject cto) {
        List<Long> filterPermissions = null;
        FilterAndSortCriteria roleCriteria = cto.getCriteriaMap().get("allRoles");
        if (roleCriteria != null) {
            for (String id : roleCriteria.getFilterValues()) {
                List<AdminPermission> permissions = retrivalService.findPermissionsForRoleId(Long.parseLong(id));
                if (permissions != null) {
                    if (filterPermissions == null) {
                        filterPermissions = new ArrayList<>();
                    }
                    for (AdminPermission perm : permissions) {
                        filterPermissions.add(perm.getId());
                    }
                }
            }
        }
        return filterPermissions;
    }
    
    protected List<Long> filterPermissionsForUser(CriteriaTransferObject cto) {
        List<Long> filterPermissions = null;
        FilterAndSortCriteria userCriteria = cto.getCriteriaMap().get("allUsers");
        if (userCriteria != null) {
            for (String id : userCriteria.getFilterValues()) {
                AdminUser user = securityService.readAdminUserById(Long.parseLong(id));
                List<AdminPermission> permissions = retrivalService.findPermissionsForAdminUser(user);
                if (permissions != null) {
                    if (filterPermissions == null) {
                        filterPermissions = new ArrayList<>();
                    }
                }
                for (AdminPermission perm : permissions) {
                    filterPermissions.add(perm.getId());
                }
            }
        }
        return filterPermissions;
    }
    
    protected void addFriendlyRestriction(CriteriaTransferObject cto) {
        cto.add(new FilterAndSortCriteria("isFriendly", "true", cto.getCriteriaMap().size()));
    }

    protected void addDefaultSort(CriteriaTransferObject cto) {
        boolean userSort = false;
        for (FilterAndSortCriteria fasc : cto.getCriteriaMap().values()) {
            if (fasc.getSortDirection() != null) {
                userSort = true;
                break;
            }
        }
        if (!userSort) {
            FilterAndSortCriteria descriptionSort = cto.getCriteriaMap().get("description");
            if (descriptionSort == null) {
                descriptionSort = new FilterAndSortCriteria("description");
                cto.add(descriptionSort);
            }
            descriptionSort.setSortAscending(true);
        }
    }

}
