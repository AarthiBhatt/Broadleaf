--
-- This file contains the out of box menus items (modules and sections) for the admin and their corresponding
-- permission mappings.
--
-- Client systems can choose new modules and associated permission mappings by deleting rows they don't want and adding
-- others that make sense for their admin.
--
-- Broadleaf Commerce add-on modules will add rows to these tables as needed.
--

--
-- Create BLC MODULES (These modules are required for the admin left navigation)
--

INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-4,'Security','BLCOpenAdmin', 'blc-icon-security', 600);
INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-5,'Settings','BLCModuleConfiguration', 'blc-icon-settings', 700);

--
-- Create BLC SECTIONS (These modules are required for the admin left navigation)
--
INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-11, 2000, -5, 'User Management', 'UserManagement', '/user-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminUser');
INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-12, 3000, -5, 'Role Management', 'RoleManagement', '/role-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminRole');

INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-13, 10000, -5, 'Configuration Management', 'ConfigurationManagement', '/configuration-management', 'org.broadleafcommerce.common.config.domain.ModuleConfiguration');

-- INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-14, 2000, -5, 'Enumerations', 'Enumerations', '/enumerations', 'org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration');

-- Permission Management
INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-17, 11000, -5, 'Permission Management', 'PermissionManagement', '/permission-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminPermission');


--
--
-- Mapping from Sections to Permissions
--
-- User Management
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-11,-120);
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-11,-121);

-- Role Management
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-12,-140);
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-12,-141);

-- Permission Management added later, see below ...

-- Configuration Managament
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-13,-126);
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-13,-127);

-- Data Driven Enumerations
-- INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-14,-128);
-- INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-14,-129);

-- Permission Management
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-17,-150);
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (-17,-151);