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
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;

import java.util.List;
import java.util.Set;

public interface AdminSecurityRetrivalService {

    /**
     * Similar to {@link #findAllPermissionsForAdminUser(AdminUser)} except it will
     * drill down and return the children permissions as well into a flatend list
     * 
     * @param adminuser
     * @return
     */
    public List<AdminPermission> findFlatenedPermissionsForAdminUser(AdminUser adminUser);
    
    /**
     * Returns all of the permissions and all of the permissions from roles for an admin user
     * 
     * @param adminUser
     * @return
     */
    public List<AdminPermission> findAllPermissionsForAdminUser(AdminUser adminUser);
    
    /**
     * Returns the permissions directly related to the admin user and not related to any roles the user has
     * 
     * @param adminUser
     * @return
     */
    public List<AdminPermission> findPermissionsForAdminUser(AdminUser adminUser);
    
    public List<AdminRole> findAllRolesForAdminUser(AdminUser adminUser);
    
    public AdminModule findAdminModuleForSection(AdminSection section);
    
    public List<AdminSection> findAdminSectionsForModule(AdminModule module);
    
    public List<AdminPermission> findPermissionsForSection(AdminSection section);
    
    public List<AdminPermission> findPermissionsForRole(AdminRole role);
    
    public List<AdminPermission> findPermissionsForRoleId(Long id);
    
    public void clearRolesForAdminUser(AdminUser adminUser);
    
    public void addRolesToAdminUser(AdminUser adminUser, Set<AdminRole> roles);
    
//    public List<AdminPermission> findQualifiedPermissions(List<String> testClasses, PermissionType type);
//    
//    public List<AdminModule> findAllAdminModules();
}
