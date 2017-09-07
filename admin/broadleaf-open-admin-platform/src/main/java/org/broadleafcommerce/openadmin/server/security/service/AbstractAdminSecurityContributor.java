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

import org.broadleafcommerce.openadmin.server.security.domain.AdminModule;
import org.broadleafcommerce.openadmin.server.security.domain.AdminModuleImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRoleImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSectionImpl;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractAdminSecurityContributor implements AdminSecurityContributor {

    protected Map<String, AdminModule> moduleMap = new HashMap<>();
    protected List<AdminSection> sections = new ArrayList<>();
    protected Map<String, List<AdminPermission>> qualifierMap = new HashMap<>();
    protected List<AdminRole> adminRoles = new ArrayList<>();
    protected Map<Long, List<AdminPermission>> rolePermissionMap = new HashMap<>();
    protected Map<Long, AdminPermission> adminPermissionMap = new HashMap<>();
    
    public AbstractAdminSecurityContributor() {
        createPermissions();
    }
    
    @Override
    public Map<String, AdminModule> getAllAdminModuleMap() {
        createAdminModules();
        return moduleMap;
    }

    @Override
    public List<AdminSection> getAdminSections(Map<String, AdminModule> moduleMap) {
        this.moduleMap = moduleMap;
        createAdminSections();
        return sections;
    }
    
    @Override
    public Map<String, List<AdminPermission>> getEntityPermissionMap() {
        createAdminPermissionEntities();
        return qualifierMap;
    }
    
    @Override
    public Map<Long, List<AdminPermission>> getRolePermissionMap() {
        addPermissionsToRoles();
        return rolePermissionMap;
    }
    
    @Override
    public List<AdminPermission> getAllAdminPermissions() {
        return new ArrayList<>(adminPermissionMap.values());
    }
    
    @Override
    public List<AdminRole> getAllAdminRoles() {
        createAdminRoles();
        return adminRoles;
    }
    
    @Override
    public void modifyAdminSections(List<AdminSection> sections) {
        // Doing nothing by default and most contributors won't need to implement this
    }
    
    protected AdminSection createSection(String name, String sectionKey, String url, String ceilingEntity, 
                                         String moduleKey, Integer order, List<AdminPermission> permissions, Long id) {
        AdminModule module = moduleMap.get(moduleKey);
        AdminSectionImpl section = new AdminSectionImpl();
        section.setName(name);
        section.setSectionKey(sectionKey);
        section.setUrl(url);
        section.setCeilingEntity(ceilingEntity);
        section.setModule(module);
        section.setDisplayOrder(order);
        section.setPermissions(permissions);
        section.setId(id);
        ((AdminModuleImpl) module).getSections().add(section);
        sections.add(section);
        return section;
    }
    
    protected AdminModule createModule(String name, String moduleKey, String icon, Integer order, Long id) {
        AdminModule module = new AdminModuleImpl();
        module.setName(name);
        module.setModuleKey(moduleKey);
        module.setIcon(icon);
        module.setDisplayOrder(order);
        module.setId(id);
        moduleMap.put(moduleKey, module);
        return module;
    }
    
    protected AdminPermission createPermission(String name, PermissionType type, Boolean isFriendly, Long id, String description) {
        AdminPermission permission = new AdminPermissionImpl();
        permission.setName(name);
        permission.setType(type);
        permission.setFriendly(isFriendly);
        permission.setId(id);
        permission.setDescription(description);
        adminPermissionMap.put(id, permission);
        return permission;
    }
    
    protected void createPermissionEntity(String ceilingEntity, AdminPermission permission) {
        List<AdminPermission> matched = qualifierMap.get(ceilingEntity);
        if (matched == null) {
            matched = new ArrayList<>();
        }
        matched.add(permission);
        qualifierMap.put(ceilingEntity, matched);
    }
    
    protected void createRole(Long id, String name, String description) {
        AdminRoleImpl role = new AdminRoleImpl();
        role.setId(id);
        role.setName(name);
        role.setDescription(description);
        adminRoles.add(role);
    }
    
    protected void addPermissionToRole(Long roleId, Long permissionId) {
        List<AdminPermission> permissions = rolePermissionMap.get(roleId);
        AdminPermission permission = adminPermissionMap.get(permissionId);
        if (permission == null) {
            throw new IllegalArgumentException("No permission exists with id " + permissionId + " when adding permission to role");
        }
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        permissions.add(permission);
        rolePermissionMap.put(roleId, permissions);
    }
    
    protected void createAdminPermissionEntitiesForPermission(AdminPermission permission, List<String> ceilingEntities) {
        for (String ceilingEntity : ceilingEntities) {
            createPermissionEntity(ceilingEntity, permission);
        }
    }
    
    protected AdminPermission findPermissionById(Long id) {
        AdminPermission permission = adminPermissionMap.get(id);
        if (permission == null) {
            throw new IllegalArgumentException("No permission exists with id " + id + " when retrieving permission");
        }
        return permission;
    }
    
    protected void addChildPermissionToPermission(Long parentId, Long... childIds) {
        AdminPermission parent = findPermissionById(parentId);
        if (parent == null) {
            throw new IllegalArgumentException("No permission found with id " + parentId + " when trying to add child ids to it");
        }
        List<AdminPermission> children = new ArrayList<>();
        for (Long id : childIds) {
            AdminPermission child = findPermissionById(id);
            if (child == null) {
                throw new IllegalArgumentException("No child permission with id " + id + " found when trying to add it as a child permission to parent with id " + parentId);
            }
            children.add(child);
        }
        parent.setChildPermissions(children);
    }
    
    protected abstract void createPermissions();
    protected abstract void createAdminModules();
    protected abstract void createAdminSections();
    protected abstract void createAdminPermissionEntities();
    protected abstract void createAdminRoles();
    protected abstract void addPermissionsToRoles();
    
    protected static class ModuleKeys {
        public static final String CATALOG = "BLCMerchandising";
        public static final String PRICING = "BLCPricing";
        public static final String CONTENT = "BLCContentManagement";
        public static final String INVENTORY = "BLCInventory";
        public static final String DESIGN = "BLCDesign";
        public static final String SITE_UPDATES = "BLCWorkflow";
        public static final String CUSTOMER_CARE = "BLCCustomerCare";
        public static final String SECURITY = "BLCOpenAdmin";
        public static final String SETTINGS = "BLCModuleConfiguration";
    }
    
    protected static class SectionKeys {
        public static final String CATEGORY = "Category";
        public static final String PRODUCT = "Product";
        public static final String PRODUCT_OPTIONS = "ProductOptions";
        public static final String OFFER = "Offer";
        public static final String PAGES = "Pages";
        public static final String ASSETS = "Assets";
        public static final String REDIRECT_URL = "RedirectURL";
        public static final String CUSTOMER = "Customer";
        public static final String USER_MANAGEMENT = "UserManagement";
        public static final String ROLE_MANAGEMENT = "RoleManagement";
        public static final String CONFIGURATION_MANAGEMENT = "ConfigurationManagement";
        public static final String PERMISSION_MANAGEMENT = "PermissionManagement";
    }
}
