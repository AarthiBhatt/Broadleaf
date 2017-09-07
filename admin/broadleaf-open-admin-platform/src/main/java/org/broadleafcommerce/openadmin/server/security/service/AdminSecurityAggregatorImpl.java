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
package org.broadleafcommerce.openadmin.server.security.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.openadmin.server.security.domain.AdminModule;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("blAdminSecurityAggregator")
public class AdminSecurityAggregatorImpl implements AdminSecurityAggregator {

    private static final Log LOG = LogFactory.getLog(AdminSecurityAggregatorImpl.class);
    
    protected List<AdminModule> adminModules;
    protected Map<String, AdminModule> adminModuleMap;
    protected List<AdminSection> adminSections;
    protected Map<String, List<AdminSection>> entitySectionMap;
    protected Map<String, List<AdminPermission>> entityPermissionMap;
    protected Map<Long, List<AdminPermission>> rolePermissionMap;
    protected List<AdminPermission> adminPermissions;
    protected List<AdminPermission> friendlyPermissions;
    protected List<AdminRole> adminRoles;
    
    public AdminSecurityAggregatorImpl(List<AdminSecurityContributor> contributors) {
        // Build modules first since they're needed to build sections
        adminModuleMap = buildModuleMap(contributors);
        adminModules = new ArrayList<>(adminModuleMap.values());
        
        // Build sections
        adminSections = buildSectionList(contributors, adminModuleMap);
        entitySectionMap = buildEntitySectionMap();
        
        // Build entity permissions
        entityPermissionMap = buildPermissionEntities(contributors);
        
        // Modify sections now that all of the building is done
        modifySections(contributors, adminSections);
        
        // Build roles
        adminRoles = buildAdminRoles(contributors);
        
        // Build role permssion map
        rolePermissionMap = buildPermissionRoleMap(contributors);
        
        // Get permission list last because permissions will be added in the contributors
        // throughout the building of the other parts
        adminPermissions = buildPermissionList(contributors);
        friendlyPermissions = buildFriendlyPermissionList(adminPermissions);
    }
    
    @Override
    public List<AdminModule> getAllAdminModules() {
        return adminModules;
    }

    @Override
    public List<AdminSection> getAllAdminSections() {
        return adminSections;
    }
    
    @Override
    public List<AdminPermission> getAllAdminPermissions() {
        return adminPermissions;
    }

    @Override
    public List<AdminPermission> getAllFriendlyPermissions() {
        return friendlyPermissions;
    }

    @Override
    public List<AdminSection> getAdminSectionsByClass(String className) {
        return entitySectionMap.get(className);
    }
    
    @Override
    public Map<String, List<AdminPermission>> getEntityPermissionMap() {
        return entityPermissionMap;
    }
    
    @Override
    public List<AdminPermission> getPermissionsForRoleId(Long roleId) {
        return rolePermissionMap.get(roleId);
    }
    
    @Override
    public List<AdminRole> getAllAdminRoles() {
        return adminRoles;
    }
    
    protected Map<String, AdminModule> buildModuleMap(List<AdminSecurityContributor> contributors) {
        Map<String, AdminModule> moduleMap = new HashMap<>();
        for (AdminSecurityContributor contributor : contributors) {
            moduleMap.putAll(contributor.getAllAdminModuleMap());
        }
        return moduleMap;
    }
    
    protected List<AdminSection> buildSectionList(List<AdminSecurityContributor> contributors, Map<String, AdminModule> moduleMap) {
        List<AdminSection> sections = new ArrayList<>();
        for (AdminSecurityContributor contributor : contributors) {
            sections.addAll(contributor.getAdminSections(moduleMap));
        }
        return sections;
    }
    
    protected Map<String, List<AdminSection>> buildEntitySectionMap() {
        List<AdminSection> sections = getAllAdminSections();
        Map<String, List<AdminSection>> entitySectionMap = new HashMap<>();
        for (AdminSection section : sections) {
            List<AdminSection> matched = entitySectionMap.get(section.getCeilingEntity());
            if (matched == null) {
                matched = new ArrayList<>();
            }
            matched.add(section);
            entitySectionMap.put(section.getCeilingEntity(), matched);
        }
        return entitySectionMap;
    }
    
    protected Map<String, List<AdminPermission>> buildPermissionEntities(List<AdminSecurityContributor> contributors) {
        Map<String, List<AdminPermission>> resultingMap = new HashMap<>();
        for (AdminSecurityContributor contributor : contributors) {
            Map<String, List<AdminPermission>> contribMap = contributor.getEntityPermissionMap();
            for (String ceilingEntity : contribMap.keySet()) {
                List<AdminPermission> dtos = contribMap.get(ceilingEntity);
                List<AdminPermission> existingDtos = resultingMap.get(ceilingEntity);
                if (existingDtos == null) {
                    existingDtos = new ArrayList<>();
                }
                existingDtos.addAll(dtos);
                resultingMap.put(ceilingEntity, existingDtos);
            }
        }
        return resultingMap;
    }
    
    protected void modifySections(List<AdminSecurityContributor> contributors, List<AdminSection> sections) {
        for (AdminSecurityContributor contrib : contributors) {
            contrib.modifyAdminSections(sections);
        }
    }
    
    protected Map<Long, List<AdminPermission>> buildPermissionRoleMap(List<AdminSecurityContributor> contributors) {
        Map<Long, List<AdminPermission>> rolePermissionMap = new HashMap<>();
        for (AdminSecurityContributor contributor : contributors) {
            Map<Long, List<AdminPermission>> contribRoleMap = contributor.getRolePermissionMap();
            for (Long roleId : contribRoleMap.keySet()) {
                List<AdminPermission> matched = rolePermissionMap.get(roleId);
                if (matched == null) {
                    matched = new ArrayList<>();
                }
                matched.addAll(contribRoleMap.get(roleId));
                rolePermissionMap.put(roleId, matched);
            }
        }
        return rolePermissionMap;
    }
    
    protected List<AdminPermission> buildPermissionList(List<AdminSecurityContributor> contributors) {
        Set<AdminPermission> permissions = new HashSet<>();
        for (AdminSecurityContributor contributor : contributors) {
            permissions.addAll(contributor.getAllAdminPermissions());
        }
        return new ArrayList<>(permissions);
    }
    
    protected List<AdminPermission> buildFriendlyPermissionList(List<AdminPermission> allPermissions) {
        // We simply filter by which permissions are friendly. We assume that all of the permissions
        // are in the list and will not dive deeper into the children permission for that reason
        List<AdminPermission> friendlyPermissions = new ArrayList<>();
        for (AdminPermission permission : allPermissions) {
            if (permission.isFriendly()) {
                friendlyPermissions.add(permission);
            }
        }
        return friendlyPermissions;
    }
    
    protected List<AdminRole> buildAdminRoles(List<AdminSecurityContributor> contributors) {
        Map<Long, AdminRole> adminRoleMap = new HashMap<>();
        for (AdminSecurityContributor contributor : contributors) {
            List<AdminRole> contribRoles = contributor.getAllAdminRoles();
            for (AdminRole role : contribRoles) {
                if (adminRoleMap.get(role.getId()) != null) {
                    LOG.warn("Found collision when merging all admin roles with role " + role.getId() + " with name " + role.getName());
                }
                adminRoleMap.put(role.getId(), role);
            }
        }
        return new ArrayList<>(adminRoleMap.values());
    }
}
