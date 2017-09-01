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
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSectionImpl;
import org.broadleafcommerce.openadmin.server.security.service.domain.AdminPermissionDTO;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractAdminSecurityContributor implements AdminSecurityContributor {

    protected Map<String, AdminModule> moduleMap = new HashMap<>();
    protected List<AdminSection> sections = new ArrayList<>();
    protected Map<String, List<AdminPermissionDTO>> qualifierMap = new HashMap<>();
    
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
    public Map<String, List<AdminPermissionDTO>> getEntityPermissionMap() {
        createAdminPermissionEntities();
        return qualifierMap;
    }
    
    protected AdminSection createSection(String name, String sectionKey, String url, String ceilingEntity, 
                                         String moduleKey, Integer order, List<AdminPermissionDTO> permissions) {
        AdminModule module = moduleMap.get(moduleKey);
        AdminSection section = new AdminSectionImpl();
        section.setName(name);
        section.setSectionKey(sectionKey);
        section.setUrl(url);
        section.setCeilingEntity(ceilingEntity);
        section.setModule(module);
        section.setDisplayOrder(order);
        section.setPermissionDTOs(permissions);
        module.getSections().add(section);
        sections.add(section);
        return section;
    }
    
    protected AdminModule createModule(String name, String moduleKey, String icon, Integer order) {
        AdminModule module = new AdminModuleImpl();
        module.setName(name);
        module.setModuleKey(moduleKey);
        module.setIcon(icon);
        module.setDisplayOrder(order);
        moduleMap.put(moduleKey, module);
        return module;
    }
    
    protected AdminPermissionDTO createPermission(String name, PermissionType type) {
        AdminPermissionDTO permission = new AdminPermissionDTO();
        permission.setName(name);
        permission.setType(type);
        return permission;
    }
    
    protected void createPermissionEntity(String ceilingEntity, AdminPermissionDTO permission) {
        List<AdminPermissionDTO> matched = qualifierMap.get(ceilingEntity);
        if (matched == null) {
            matched = new ArrayList<>();
        }
        matched.add(permission);
        qualifierMap.put(ceilingEntity, matched);
    }
    
    protected void createAdminPermissionEntitiesForPermission(AdminPermissionDTO permission, List<String> ceilingEntities) {
        for (String ceilingEntity : ceilingEntities) {
            createPermissionEntity(ceilingEntity, permission);
        }
    }
    
    protected abstract void createAdminModules();
    protected abstract void createAdminSections();
    protected abstract void createAdminPermissionEntities();
    
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
