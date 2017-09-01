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
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.service.domain.AdminPermissionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("blAdminSecurityAggregator")
public class AdminSecurityAggregatorImpl implements AdminSecurityAggregator {

    protected List<AdminModule> adminModules;
    protected Map<String, AdminModule> adminModuleMap;
    protected List<AdminSection> adminSections;
    protected Map<String, List<AdminSection>> entitySectionMap;
    protected Map<String, List<AdminPermissionDTO>> entityPermissionMap;
    
    public AdminSecurityAggregatorImpl(List<AdminSecurityContributor> contributors) {
        adminModuleMap = buildModuleMap(contributors);
        adminModules = new ArrayList<>(adminModuleMap.values());
        adminSections = buildSectionList(contributors, adminModuleMap);
        entitySectionMap = buildEntitySectionMap();
        entityPermissionMap = buildPermissionEntities(contributors); 
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
    public List<AdminSection> getAdminSectionsByClass(String className) {
        return entitySectionMap.get(className);
    }
    
    @Override
    public Map<String, List<AdminPermissionDTO>> getEntityPermissionMap() {
        return entityPermissionMap;
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
            sections.addAll(contributor.getAdminSectionMap(moduleMap));
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
    
    protected Map<String, List<AdminPermissionDTO>> buildPermissionEntities(List<AdminSecurityContributor> contributors) {
        Map<String, List<AdminPermissionDTO>> resultingMap = new HashMap<>();
        for (AdminSecurityContributor contributor : contributors) {
            Map<String, List<AdminPermissionDTO>> contribMap = contributor.getEntityPermissionMap();
            for (String ceilingEntity : contribMap.keySet()) {
                List<AdminPermissionDTO> dtos = contribMap.get(ceilingEntity);
                List<AdminPermissionDTO> existingDtos = resultingMap.get(ceilingEntity);
                if (existingDtos == null) {
                    existingDtos = new ArrayList<>();
                }
                existingDtos.addAll(dtos);
                resultingMap.put(ceilingEntity, existingDtos);
            }
        }
        return resultingMap;
    }

}
