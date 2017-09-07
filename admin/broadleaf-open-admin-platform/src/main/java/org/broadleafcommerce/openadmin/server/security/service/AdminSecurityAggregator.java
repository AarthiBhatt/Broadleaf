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

import java.util.List;
import java.util.Map;

public interface AdminSecurityAggregator {

    public List<AdminModule> getAllAdminModules();
    
    public List<AdminSection> getAllAdminSections();
    
    public List<AdminPermission> getAllAdminPermissions();
    
    public List<AdminPermission> getAllFriendlyPermissions();
    
    public List<AdminRole> getAllAdminRoles();
    
    public List<AdminSection> getAdminSectionsByClass(String className);
    
    public Map<String, List<AdminPermission>> getEntityPermissionMap();
    
    public List<AdminPermission> getPermissionsForRoleId(Long roleId);
    
    
}
