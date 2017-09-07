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
import org.broadleafcommerce.openadmin.server.security.domain.AdminModuleDTO;
import org.broadleafcommerce.openadmin.server.security.domain.AdminModuleImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRoleImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSectionImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUserImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

@Service("blAdminSecurityRetrivalService")
public class AdminSecurityRetrivalServiceImpl implements AdminSecurityRetrivalService {

    @Resource(name = "blAdminSecurityAggregator")
    protected AdminSecurityAggregator securityAggregator;
    
    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService securityService;
    
    @Override
    public List<AdminPermission> findFlatenedPermissionsForAdminUser(AdminUser adminUser) {
        AdminUserImpl user = (AdminUserImpl) adminUser;
        List<AdminPermission> allPermissions = new ArrayList<>();
        Set<AdminPermission> highLevelPermissions = new HashSet<>(findPermissionsForAdminUser(adminUser));
        for (AdminRole role : user.getAllRoles()) {
            highLevelPermissions.addAll(findPermissionsForRole(role));
        }
        for (AdminPermission permission : highLevelPermissions) {
            allPermissions.add(permission);
            if (permission.isFriendly()) {
                for (AdminPermission childPermission : permission.getAllChildPermissions()) {
                    allPermissions.add(childPermission);
                }
            }
        }
        return allPermissions;
    }

    @Override
    public List<AdminPermission> findAllPermissionsForAdminUser(AdminUser adminUser) {
        AdminUserImpl user = (AdminUserImpl) adminUser;
        List<AdminPermission> allPermissions = findPermissionsForAdminUser(adminUser);
        for (AdminRole role : user.getAllRoles()) {
            allPermissions.addAll(findPermissionsForRole(role));
        }
        return allPermissions;
    }

    @Override
    public List<AdminPermission> findPermissionsForAdminUser(AdminUser adminUser) {
        return new ArrayList<>(((AdminUserImpl) adminUser).getAllPermissions());
    }

    @Override
    public List<AdminRole> findAllRolesForAdminUser(AdminUser adminUser) {
        return new ArrayList<>(((AdminUserImpl) adminUser).getAllRoles());
    }

    @Override
    public AdminModule findAdminModuleForSection(AdminSection section) {
        return ((AdminSectionImpl) section).getModule();
    }

    @Override
    public List<AdminSection> findAdminSectionsForModule(AdminModule module) {
        if (module instanceof AdminModuleImpl) {
            return ((AdminModuleImpl) module).getSections();
        } else if (module instanceof AdminModuleDTO) {
            return ((AdminModuleDTO) module).getSections();
        }
        return null;
    }

    @Override
    public List<AdminPermission> findPermissionsForSection(AdminSection section) {
        return ((AdminSectionImpl) section).getPermissions();
    }

    @Override
    public List<AdminPermission> findPermissionsForRole(AdminRole role) {
        Map<Long, AdminPermission> idPermissionMap = new HashMap<>();
        if (securityAggregator.getPermissionsForRoleId(role.getId()) != null) {
            for (AdminPermission perm : securityAggregator.getPermissionsForRoleId(role.getId())) {
                idPermissionMap.put(perm.getId(), perm);
            }
        }
        if (((AdminRoleImpl) role).getAllPermissions() != null) {
            for (AdminPermission perm : ((AdminRoleImpl) role).getAllPermissions()) {
                idPermissionMap.put(perm.getId(), perm);
            }
        }
        return new ArrayList<>(idPermissionMap.values());
    }
    
    @Override
    public List<AdminPermission> findPermissionsForRoleId(Long id) {
        AdminRole role = securityService.readAdminRoleById(id);
        if (role != null) {
            return findPermissionsForRole(role);
        }
        return securityAggregator.getPermissionsForRoleId(id);
    }

    @Override
    public void clearRolesForAdminUser(AdminUser adminUser) {
        adminUser.setAllRoles(new HashSet<AdminRole>());
    }

    @Override
    public void addRolesToAdminUser(AdminUser adminUser, Set<AdminRole> roles) {
        adminUser.setAllRoles(roles);
    }
    
//    @Override
//    public List<AdminPermission> findQualifiedPermissions(List<String> testClasses, PermissionType permissionType) {
////        Map<String, List<AdminPermissionDTO>> entityPermissionMap = securityAggregator.getEntityPermissionMap();
////        List<String> qualifiedPermissionNames = new ArrayList<>();
////        for (String clazz : entityPermissionMap.keySet()) {
////            if (testClasses.contains(clazz)) {
////                for (AdminPermissionDTO dto : entityPermissionMap.get(clazz)) {
////                    if (permissionType == null || dto.getType().equals(PermissionType.ALL) || dto.getType().equals(permissionType)) {
////                        qualifiedPermissionNames.add(dto.getName());
////                    }
////                }
////            }
////        }
////        return qualifiedPermissionNames;
//        return null;
//    }
//    
//    @Override
//    public List<AdminModule> findAllAdminModules() {
//        return null;
//    }

}
