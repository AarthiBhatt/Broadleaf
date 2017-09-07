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
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("blOpenAdminSecurityContributor")
public class OpenAdminSecurityContributor extends AbstractAdminSecurityContributor {

    @Override
    protected void createAdminModules() {
        createCatalogModule();
        createPricingModule();
        createContentModule();
        createInventoryModule();
        createDesignModule();
        createSiteUpdatesModule();
        createCustomerCareModule();
        createSecurityModule();
        createSettingsModule();
    }
    
    protected AdminModule createCatalogModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-1,'Catalog','BLCMerchandising', 'blc-icon-catalog', 100);
        return createModule("Catalog", ModuleKeys.CATALOG, "blc-icon-catalog", 100, -1L);
    }
    
    protected AdminModule createPricingModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-8,'Pricing','BLCPricing', 'fa fa-usd', 150);
        return createModule("Pricing", ModuleKeys.PRICING, "fa fa-usd", 150, -8L);
    }
    
    protected AdminModule createContentModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-2,'Content','BLCContentManagement', 'blc-icon-content', 200);
        return createModule("Content", ModuleKeys.CONTENT, "blc-icon-content", 200, -2L);
    }
    
    protected AdminModule createInventoryModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-9,'Inventory','BLCInventory', 'blc-icon-inventory', 250);
        return createModule("Inventory", ModuleKeys.INVENTORY, "blc-icon-inventory", 250, -9L);
    }
    
    protected AdminModule createDesignModule() {
        //INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-6,'Design','BLCDesign', 'blc-icon-design', 400);
        return createModule("Design", ModuleKeys.DESIGN, "blc-icon-design", 400, -6L);
    }
    
    protected AdminModule createSiteUpdatesModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-7,'Site Updates','BLCWorkflow', 'blc-icon-site-updates', 500);
        return createModule("Site Updates", ModuleKeys.SITE_UPDATES, "blc-icon-site-updates", 500, -7L);
    }
    
    protected AdminModule createCustomerCareModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-3,'Customer Care','BLCCustomerCare', 'blc-icon-customer-care', 550);
        return createModule("Customer Care", ModuleKeys.CUSTOMER_CARE, "blc-icon-customer-care", 550, -3L);
    }
    
    protected AdminModule createSecurityModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-4,'Security','BLCOpenAdmin', 'blc-icon-security', 600);
        return createModule("Security", ModuleKeys.SECURITY, "blc-icon-security", 600, -4L);
    }
    
    protected AdminModule createSettingsModule() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-5,'Settings','BLCModuleConfiguration', 'blc-icon-settings', 700);
        return createModule("Settings", ModuleKeys.SETTINGS, "blc-icon-settings", 700, -5L);
    }
    
    @Override
    protected void createAdminSections() {
        createCategorySection();
        createProductSection();
        createProductOptionsSection();
        createOfferSection();
        createPagesSection();
        createAssetsSection();
        createRedirectUrlSection();
        createCustomerSection();
        createUserManagementSection();
        createRoleManagementSection();
        createConfigurationManagementSection();
        createPermissionManagementSection();
    }
    
    @Override
    protected void createPermissions() {
        // non-friendly permissions
        createPermission("PERMISSION_OTHER_DEFAULT", PermissionType.OTHER, false, -1L, "Default Permission");
                
        createPermission("PERMISSION_READ_CATEGORY", PermissionType.READ, false, -2L, "Read Category");
        createPermission("PERMISSION_ALL_CATEGORY", PermissionType.ALL, false, -3L, "All Category");
                
        createPermission("PERMISSION_READ_PRODUCT", PermissionType.READ, false, -4L, "Read Product");
        createPermission("PERMISSION_ALL_PRODUCT", PermissionType.ALL, false, -5L, "All Product");
                
        createPermission("PERMISSION_READ_PRODUCT_OPTION", PermissionType.READ, false, -6L, "Read Product Option");
        createPermission("PERMISSION_ALL_PRODUCT_OPTION", PermissionType.ALL, false, -7L, "All Product Option");
                
        createPermission("PERMISSION_READ_SKU", PermissionType.READ, false, -8L, "Read Sku");
        createPermission("PERMISSION_ALL_SKU", PermissionType.ALL, false, -9L, "All Sku");
                
        createPermission("PERMISSION_READ_PROMOTION", PermissionType.READ, false, -10L, "Read Promotion");
        createPermission("PERMISSION_ALL_PROMOTION", PermissionType.ALL, false, -11L, "All Promotion");
                
        createPermission("PERMISSION_READ_ORDER", PermissionType.READ, false, -12L, "Read Order");
        createPermission("PERMISSION_ALL_ORDER", PermissionType.ALL, false, -13L, "All Order");
                
        createPermission("PERMISSION_READ_FULFILLMENT_GROUP", PermissionType.READ, false, -14L, "Read Fulfillment Group");
        createPermission("PERMISSION_ALL_FULFILLMENT_GROUP", PermissionType.ALL, false, -15L, "All Fulfillment Group");
                
        createPermission("PERMISSION_READ_ORDER_ITEM", PermissionType.READ, false, -16L, "Read Order Item");
        createPermission("PERMISSION_ALL_ORDER_ITEM", PermissionType.ALL, false, -17L, "All Order Item");
                
        createPermission("PERMISSION_READ_CUSTOMER", PermissionType.READ, false, -18L, "Read Customer");
        createPermission("PERMISSION_ALL_CUSTOMER", PermissionType.ALL, false, -19L, "All Customer");
                
        createPermission("PERMISSION_READ_PAGE", PermissionType.READ, false, -20L, "Read Page");
        createPermission("PERMISSION_ALL_PAGE", PermissionType.ALL, false, -21L, "All Page");
                
        createPermission("PERMISSION_READ_PAGE_TEMPLATE", PermissionType.READ, false, -200L, "Read Page Template");
                
        createPermission("PERMISSION_READ_ASSET", PermissionType.READ, false, -22L, "Read Asset");
        createPermission("PERMISSION_ALL_ASSET", PermissionType.ALL, false, -23L, "All Asset");
                
        createPermission("PERMISSION_READ_ADMIN_USER", PermissionType.READ, false, -26L, "Read Admin User");
        createPermission("PERMISSION_ALL_ADMIN_USER", PermissionType.ALL, false, -27L, "All Admin User");
                
        createPermission("PERMISSION_READ_URLHANDLER", PermissionType.READ, false, -28L, "Read URLHandler");
        createPermission("PERMISSION_ALL_URLHANDLER", PermissionType.ALL, false, -29L, "All URLHandler");
                
        createPermission("PERMISSION_READ_SEARCHREDIRECT", PermissionType.READ, false, -30L, "Read SearchRedirect");
        createPermission("PERMISSION_ALL_SEARCHREDIRECT", PermissionType.ALL, false, -31L, "All SearchRedirect");
                
        createPermission("PERMISSION_READ_SEARCHFACET", PermissionType.READ, false, -32L, "Read SearchFacet");
        createPermission("PERMISSION_ALL_SEARCHFACET", PermissionType.ALL, false, -33L, "All SearchFacet");
                
        createPermission("PERMISSION_READ_CURRENCY", PermissionType.READ, false, -34L, "Read Currency");
        createPermission("PERMISSION_ALL_CURRENCY", PermissionType.ALL, false, -35L, "All Currency");
                
        createPermission("PERMISSION_READ_MODULECONFIGURATION", PermissionType.READ, false, -36L, "Read Configuration");
        createPermission("PERMISSION_ALL_MODULECONFIGURATION", PermissionType.ALL, false, -37L, "All Configuration");
                
        createPermission("PERMISSION_READ_ENUMERATION", PermissionType.READ, false, -38L, "Read Enumeration");
        createPermission("PERMISSION_ALL_ENUMERATION", PermissionType.ALL, false, -39L, "All Enumeration");
                
        createPermission("PERMISSION_READ_TRANSLATION", PermissionType.READ, false, -40L, "Read Translation");
        createPermission("PERMISSION_ALL_TRANSLATION", PermissionType.ALL, false, -41L, "All Translation");
                
        createPermission("PERMISSION_READ_SITE_MAP_GEN_CONFIG", PermissionType.READ, false, -42L, "Read Site Map Gen Configuration");
        createPermission("PERMISSION_ALL_SITE_MAP_GEN_CONFIG", PermissionType.ALL, false, -43L, "All Site Map Gen Configuration");
                
        createPermission("PERMISSION_READ_SYSTEM_PROPERTY", PermissionType.READ, false, -44L, "Read System Property");
        createPermission("PERMISSION_ALL_SYSTEM_PROPERTY", PermissionType.ALL, false, -45L, "All System Property");
                
        createPermission("PERMISSION_READ_ADMIN_ROLES", PermissionType.READ, false, -46L, "Read Admin Roles");
        createPermission("PERMISSION_ALL_ADMIN_ROLES", PermissionType.ALL, false, -47L, "All Admin Roles");
                
        createPermission("PERMISSION_READ_ADMIN_PERMS", PermissionType.READ, false, -48L, "Read Admin Permissions");
        createPermission("PERMISSION_ALL_ADMIN_PERMS", PermissionType.ALL, false, -49L, "All Admin Permissions");
                
        createPermission("PERMISSION_READ_FIELD_DEFS", PermissionType.READ, false, -50L, "Read Field Definitions");
        createPermission("PERMISSION_ALL_FIELD_DEFS", PermissionType.ALL, false, -51L, "All Field Definitions");
                
        createPermission("PERMISSION_READ_CATALOG_PERMS", PermissionType.READ, false, -52L, "Read Catalog Permissions");
        createPermission("PERMISSION_ALL_CATALOG_PERMS", PermissionType.ALL, false, -53L, "All Catalog Permissions");
                
        createPermission("PERMISSION_READ_ISO_COUNTRIES", PermissionType.READ, false, -54L, "Read ISO Countries");
        createPermission("PERMISSION_ALL_ISO_COUNTRIES", PermissionType.ALL, false, -55L, "All ISO COUNTRIES");
        
        // Friendly Permissions
        createPermission("PERMISSION_CATEGORY", PermissionType.READ, true, -100L, "View Categories");
        createPermission("PERMISSION_CATEGORY", PermissionType.ALL, true, -101L, "Maintain Categories");
        
        createPermission("PERMISSION_PRODUCT", PermissionType.READ, true, -102L, "View Products");
        createPermission("PERMISSION_PRODUCT", PermissionType.ALL, true, -103L, "Maintain Products");
        
        createPermission("PERMISSION_PRODUCTOPTIONS", PermissionType.READ, true, -104L, "View Product Options");
        createPermission("PERMISSION_PRODUCTOPTIONS", PermissionType.ALL, true, -105L, "Maintain Product Options");
        
        createPermission("PERMISSION_OFFER", PermissionType.READ, true, -106L, "View Offers");
        createPermission("PERMISSION_OFFER", PermissionType.ALL, true, -107L, "Maintain Offers");
        
        createPermission("PERMISSION_PAGE", PermissionType.READ, true, -108L, "View Pages");
        createPermission("PERMISSION_PAGE", PermissionType.ALL, true, -109L, "Maintain Pages");
        
        createPermission("PERMISSION_ASSET", PermissionType.READ, true, -110L, "View Assets");
        createPermission("PERMISSION_ASSET", PermissionType.ALL, true, -111L, "Maintain Assets");
        
        createPermission("PERMISSION_URLREDIRECT", PermissionType.READ, true, -114L, "View URL Redirects");
        createPermission("PERMISSION_URLREDIRECT", PermissionType.ALL, true, -115L, "Maintain URL Redirects");
        
        createPermission("PERMISSION_CUSTOMER", PermissionType.READ, true, -118L, "View Customers");
        createPermission("PERMISSION_CUSTOMER", PermissionType.ALL, true, -119L, "Maintain Customers");
        
        createPermission("PERMISSION_USER", PermissionType.READ, true, -120L, "View Users");
        createPermission("PERMISSION_USER", PermissionType.ALL, true, -121L, "Maintain Users");
        
        createPermission("PERMISSION_MODULECONFIGURATION", PermissionType.READ, true, -126L, "View Module Configuration");
        createPermission("PERMISSION_MODULECONFIGURATION", PermissionType.ALL, true, -127L, "Maintain Module Configuration");
        
        createPermission("PERMISSION_TRANSLATION", PermissionType.READ, true, -130L, "View Translations");
        createPermission("PERMISSION_TRANSLATION", PermissionType.ALL, true, -131L, "Maintain Translations");
        
        createPermission("PERMISSION_ROLE_VIEW", PermissionType.READ, true, -140L, "View Roles");
        createPermission("PERMISSION_ROLE_ALL", PermissionType.ALL, true, -141L, "Maintain Roles");
        
        createPermission("PERMISSION_PERM_VIEW", PermissionType.READ, true, -150L, "View Permissions");
        createPermission("PERMISSION_PERM_ALL", PermissionType.ALL, true, -151L, "Maintain Permissions");
        
        createPermission("PERMISSION_FLDDEF_VIEW", PermissionType.READ, true, -160L, "View Field Definitions");
        createPermission("PERMISSION_FLDDEF_ALL", PermissionType.ALL, true, -161L, "Maintain Field Definitions");
        
        createPermission("PERMISSION_PROMOTION_MESSAGE", PermissionType.READ, true, -180L, "View Promotion Messages");
        createPermission("PERMISSION_PROMOTION_MESSAGE", PermissionType.ALL, true, -181L, "Maintain Promotion Messages");
        
        addChildPermissionToPermission(-100L, -2L, -4L, -32L, -53L);
        addChildPermissionToPermission(-101L, -3L, -4L, -32L, -53L);
        addChildPermissionToPermission(-102L, -4L, -6L, -8L, -34L);
        addChildPermissionToPermission(-103L, -5L, -6L, -9L, -34L, -53L);
        addChildPermissionToPermission(-104L, -6L, -32L, -53L);
        addChildPermissionToPermission(-105L, -7L, -32L, -53L);
        addChildPermissionToPermission(-106L, -10L, -53L, -180L);
        addChildPermissionToPermission(-107L, -11L, -53L, -181L);
        addChildPermissionToPermission(-108L, -20L, -200L);
        addChildPermissionToPermission(-109L, -21L, -200L);
        addChildPermissionToPermission(-110L, -22L);
        addChildPermissionToPermission(-111L, -23L);
        addChildPermissionToPermission(-114L, -28L);
        addChildPermissionToPermission(-115L, -29L);
        addChildPermissionToPermission(-118L, -18L);
        addChildPermissionToPermission(-119L, -19L);
        addChildPermissionToPermission(-120L, -26L, -46L, -48L);
        addChildPermissionToPermission(-121L, -27L, -46L, -48L);
        addChildPermissionToPermission(-126L, -36L);
        addChildPermissionToPermission(-127L, -37L);
        addChildPermissionToPermission(-130L, -40L);
        addChildPermissionToPermission(-131L, -41L);
        addChildPermissionToPermission(-140L, -46L, -48L);
        addChildPermissionToPermission(-141L, -47L, -48L);
        addChildPermissionToPermission(-150L, -48L);
        addChildPermissionToPermission(-151L, -49L);
        addChildPermissionToPermission(-160L, -50L);
        addChildPermissionToPermission(-161L, -51L);
        
    }
    
    protected AdminSection createCategorySection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-100L));
        permissions.add(findPermissionById(-101L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-1, 2000, -1, 'Category', 'Category', '/category', 'org.broadleafcommerce.core.catalog.domain.Category');
        AdminSection categorySection = createSection("Category", SectionKeys.CATEGORY, "/category", "org.broadleafcommerce.core.catalog.domain.Category", ModuleKeys.CATALOG, 2000, permissions, -1L);
        return categorySection;
    }
    
    protected AdminSection createProductSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-102L));
        permissions.add(findPermissionById(-103L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-2, 3000, -1, 'Product', 'Product', '/product', 'org.broadleafcommerce.core.catalog.domain.Product');
        return createSection("Product", SectionKeys.PRODUCT, "/product", "org.broadleafcommerce.core.catalog.domain.Product", ModuleKeys.CATALOG, 3000, permissions, -2L);
    }
    
    protected AdminSection createProductOptionsSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-104L));
        permissions.add(findPermissionById(-105L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-3, 5000, -1, 'Product Options', 'ProductOptions', '/product-options', 'org.broadleafcommerce.core.catalog.domain.ProductOption');
        return createSection("Product Options", SectionKeys.PRODUCT_OPTIONS, "/product-options", "org.broadleafcommerce.core.catalog.domain.ProductOption", ModuleKeys.CATALOG, 5000, permissions, -3L);
    }
    
    protected AdminSection createOfferSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-106L));
        permissions.add(findPermissionById(-107L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-4, 1000, -8, 'Offer', 'Offer', '/offer', 'org.broadleafcommerce.core.offer.domain.Offer');
        return createSection("Offer", SectionKeys.OFFER, "/offer", "org.broadleafcommerce.core.offer.domain.Offer", ModuleKeys.PRICING, 1000, permissions, -4L);
    }
    
    protected AdminSection createPagesSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-108L));
        permissions.add(findPermissionById(-109L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-5, 2000, -2, 'Pages', 'Pages', '/pages', 'org.broadleafcommerce.cms.page.domain.Page');
        return createSection("Pages", SectionKeys.PAGES, "/pages", "org.broadleafcommerce.cms.page.domain.Page", ModuleKeys.CONTENT, 2000, permissions, -5L);
    }
    
    protected AdminSection createAssetsSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-110L));
        permissions.add(findPermissionById(-111L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-6, 4000, -2, 'Assets', 'Assets', '/assets', 'org.broadleafcommerce.cms.file.domain.StaticAsset');
        return createSection("Assets", SectionKeys.ASSETS, "/assets", "org.broadleafcommerce.cms.file.domain.StaticAsset", ModuleKeys.CONTENT, 4000, permissions, -6L);
    }
    
    protected AdminSection createRedirectUrlSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-114L));
        permissions.add(findPermissionById(-115L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-8, 7000, -2, 'Redirect URL', 'RedirectURL', '/redirect-url', 'org.broadleafcommerce.cms.url.domain.URLHandler');
        return createSection("Redirect URL", SectionKeys.REDIRECT_URL, "/redirect-url", "org.broadleafcommerce.cms.url.domain.URLHandler", ModuleKeys.CONTENT, 7000, permissions, -8L);
    }
    
    protected AdminSection createCustomerSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-118L));
        permissions.add(findPermissionById(-119L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-10, 1000, -3,'Customer', 'Customer', '/customer', 'org.broadleafcommerce.profile.core.domain.Customer');
        return createSection("Customer", SectionKeys.CUSTOMER, "/customer", "org.broadleafcommerce.profile.core.domain.Customer", ModuleKeys.CUSTOMER_CARE, 1000, permissions, -10L);
    }
    
    protected AdminSection createUserManagementSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-120L));
        permissions.add(findPermissionById(-121L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-11, 2000, -5, 'User Management', 'UserManagement', '/user-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminUser');
        return createSection("User Management", SectionKeys.USER_MANAGEMENT, "/user-management", "org.broadleafcommerce.openadmin.server.security.domain.AdminUser", ModuleKeys.SETTINGS, 2000, permissions, -11L);
    }
    
    protected AdminSection createRoleManagementSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-140L));
        permissions.add(findPermissionById(-141L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-12, 3000, -5, 'Role Management', 'RoleManagement', '/role-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminRole');
        return createSection("Role Management", SectionKeys.ROLE_MANAGEMENT, "/role-management", "org.broadleafcommerce.openadmin.server.security.domain.AdminRole", ModuleKeys.SETTINGS, 3000, permissions, -12L);
    }
    
    protected AdminSection createConfigurationManagementSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-126L));
        permissions.add(findPermissionById(-127L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-13, 10000, -5, 'Configuration Management', 'ConfigurationManagement', '/configuration-management', 'org.broadleafcommerce.common.config.domain.ModuleConfiguration');
        return createSection("Configuration Management", SectionKeys.CONFIGURATION_MANAGEMENT, "/configuration-management", "org.broadleafcommerce.common.config.domain.ModuleConfiguration", ModuleKeys.SETTINGS, 10000, permissions, -13L);
    }
    
    protected AdminSection createPermissionManagementSection() {
        ArrayList<AdminPermission> permissions = new ArrayList<>();
        permissions.add(findPermissionById(-150L));
        permissions.add(findPermissionById(-151L));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-17, 11000, -5, 'Permission Management', 'PermissionManagement', '/permission-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminPermission');
        return createSection("Permission Management", SectionKeys.PERMISSION_MANAGEMENT, "/permission-management", "org.broadleafcommerce.openadmin.server.security.domain.AdminPermission", ModuleKeys.SETTINGS, 11000, permissions, -17L);
    }
    
    @Override
    protected void createAdminPermissionEntities() {
        {
            AdminPermission viewCategory = findPermissionById(-2L);
            AdminPermission maintainCategory = findPermissionById(-3L);
            List<String> categoryEntities = Arrays.asList(
                    "org.broadleafcommerce.core.catalog.domain.Category",
                    "org.broadleafcommerce.core.catalog.domain.CategoryAttribute",
                    "org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl",
                    "org.broadleafcommerce.core.catalog.domain.CategoryXrefImpl",
                    "org.broadleafcommerce.core.catalog.domain.FeaturedProductImpl",
                    "org.broadleafcommerce.core.catalog.domain.CrossSaleProductImpl",
                    "org.broadleafcommerce.core.catalog.domain.UpSaleProductImpl"
            );
            createAdminPermissionEntitiesForPermission(viewCategory, categoryEntities);
            createAdminPermissionEntitiesForPermission(maintainCategory, categoryEntities);
        }
        {
            AdminPermission viewProduct = findPermissionById(-4L);
            AdminPermission maintainProduct = findPermissionById(-5L);
            List<String> productEntities = Arrays.asList(
                    "org.broadleafcommerce.core.catalog.domain.Product",
                    "org.broadleafcommerce.core.catalog.domain.ProductAttribute",
                    "org.broadleafcommerce.core.catalog.domain.UpSaleProductImpl",
                    "org.broadleafcommerce.core.catalog.domain.SkuBundleItemImpl"
            );
            createAdminPermissionEntitiesForPermission(viewProduct, productEntities);
            createAdminPermissionEntitiesForPermission(maintainProduct, productEntities);
        }
        
        {
            AdminPermission viewProductOption = findPermissionById(-6L);
            AdminPermission maintainProductOption = findPermissionById(-7L);
            List<String> productOptionEntities = Arrays.asList(
                    "org.broadleafcommerce.core.catalog.domain.ProductOption",
                    "org.broadleafcommerce.core.catalog.domain.ProductOptionValue",
                    "org.broadleafcommerce.core.catalog.domain.ProductOptionXref",
                    "org.broadleafcommerce.core.catalog.domain.SkuProductOptionValueXref"
            );
            createAdminPermissionEntitiesForPermission(viewProductOption, productOptionEntities);
            createAdminPermissionEntitiesForPermission(maintainProductOption, productOptionEntities);
        }
        
        {
            AdminPermission viewSku = findPermissionById(-8L);
            AdminPermission maintainSku = findPermissionById(-9L);
            List<String> skuEntities = Arrays.asList(
                    "org.broadleafcommerce.core.catalog.domain.Sku"
            );
            createAdminPermissionEntitiesForPermission(viewSku, skuEntities);
            createAdminPermissionEntitiesForPermission(maintainSku, skuEntities);
        }
        
        {
            AdminPermission viewOffer = findPermissionById(-10L);
            AdminPermission maintainOffer = findPermissionById(-11L);
            List<String> offerEntities = Arrays.asList(
                    "org.broadleafcommerce.core.offer.domain.Offer",
                    "org.broadleafcommerce.core.offer.domain.OfferItemCriteria",
                    "org.broadleafcommerce.core.offer.domain.OfferCode",
                    "org.broadleafcommerce.core.offer.domain.OfferTier"
            );
            createAdminPermissionEntitiesForPermission(viewOffer, offerEntities);
            createAdminPermissionEntitiesForPermission(maintainOffer, offerEntities);
        }
        
        {
            AdminPermission viewOrder = findPermissionById(-12L);
            AdminPermission maintainOrder = findPermissionById(-13L);
            List<String> orderEntities = Arrays.asList(
                    "org.broadleafcommerce.core.order.domain.Order",
                    "org.broadleafcommerce.core.offer.domain.OrderAdjustment",
                    "org.broadleafcommerce.core.payment.domain.OrderPayment",
                    "org.broadleafcommerce.profile.core.domain.Country",
                    "org.broadleafcommerce.profile.core.domain.State",
                    "org.broadleafcommerce.core.payment.domain.PaymentTransactionImpl"
            );
            createAdminPermissionEntitiesForPermission(viewOrder, orderEntities);
            createAdminPermissionEntitiesForPermission(maintainOrder, orderEntities);
        }
        
        {
            AdminPermission viewFg = findPermissionById(-14L);
            AdminPermission maintainFg = findPermissionById(-15L);
            List<String> fgEntities = Arrays.asList(
                    "org.broadleafcommerce.core.order.domain.FulfillmentGroup",
                    "org.broadleafcommerce.core.offer.domain.FulfillmentGroupAdjustment",
                    "org.broadleafcommerce.core.order.domain.FulfillmentGroupFeeImpl",
                    "org.broadleafcommerce.core.order.domain.FulfillmentGroupItemImpl"

            );
            createAdminPermissionEntitiesForPermission(viewFg, fgEntities);
            createAdminPermissionEntitiesForPermission(maintainFg, fgEntities);
        }
        
        {
            AdminPermission viewOi = findPermissionById(-16L);
            AdminPermission maintainOi = findPermissionById(-17L);
            List<String> oiEntities = Arrays.asList(
                    "org.broadleafcommerce.core.order.domain.OrderItem",
                    "org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice",
                    "org.broadleafcommerce.core.offer.domain.OrderItemAdjustment",
                    "org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustmentImpl",
                    "org.broadleafcommerce.core.order.domain.OrderItemPriceDetailImpl",
                    "org.broadleafcommerce.core.order.domain.BundleOrderItemFeePriceImpl"
            );
            createAdminPermissionEntitiesForPermission(viewOi, oiEntities);
            createAdminPermissionEntitiesForPermission(maintainOi, oiEntities);
        }
        
        {
            AdminPermission viewCustomer = findPermissionById(-18L);
            AdminPermission maintainCustomer = findPermissionById(-19L);
            List<String> customerEntities = Arrays.asList(
                    "org.broadleafcommerce.profile.core.domain.Customer",
                    "org.broadleafcommerce.profile.core.domain.ChallengeQuestion",
                    "org.broadleafcommerce.profile.core.domain.CustomerAttribute",
                    "org.broadleafcommerce.profile.core.domain.CustomerAddress",
                    "org.broadleafcommerce.profile.core.domain.CustomerPayment",
                    "org.broadleafcommerce.profile.core.domain.CustomerPhone",
                    "org.broadleafcommerce.core.catalog.domain.CrossSaleProductImpl"
            );
            createAdminPermissionEntitiesForPermission(viewCustomer, customerEntities);
            createAdminPermissionEntitiesForPermission(maintainCustomer, customerEntities);
        }
        
        {
            AdminPermission viewPage = findPermissionById(-20L);
            AdminPermission maintainPage = findPermissionById(-21L);
            List<String> pageEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.page.domain.Page",
                    "org.broadleafcommerce.cms.page.domain.PageItemCriteria",
                    "org.broadleafcommerce.common.locale.domain.Locale"
            );
            createAdminPermissionEntitiesForPermission(viewPage, pageEntities);
            createAdminPermissionEntitiesForPermission(maintainPage, pageEntities);
        }
        
        {
            AdminPermission viewPageTemplate = findPermissionById(-200L);
            List<String> pageTemplateEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.page.domain.PageTemplate"
            );
            createAdminPermissionEntitiesForPermission(viewPageTemplate, pageTemplateEntities);
        }
        
        {
            AdminPermission viewAsset = findPermissionById(-22L);
            AdminPermission maintainAsset = findPermissionById(-23L);
            List<String> assetEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.file.domain.StaticAsset",
                    "org.broadleafcommerce.cms.file.domain.StaticAssetFolder"
            );
            createAdminPermissionEntitiesForPermission(viewAsset, assetEntities);
            createAdminPermissionEntitiesForPermission(maintainAsset, assetEntities);
        }
        
        {
            AdminPermission viewUser = findPermissionById(-26L);
            AdminPermission maintainUser = findPermissionById(-27L);
            List<String> userEntities = Arrays.asList(
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminUser"
            );
            createAdminPermissionEntitiesForPermission(viewUser, userEntities);
            createAdminPermissionEntitiesForPermission(maintainUser, userEntities);
        }
        
        {
            AdminPermission viewUrlHandlers = findPermissionById(-28L);
            AdminPermission maintainUrlHandlers = findPermissionById(-29L);
            List<String> urlHandlersEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.url.domain.URLHandler",
                    "org.broadleafcommerce.common.locale.domain.Locale"
            );
            createAdminPermissionEntitiesForPermission(viewUrlHandlers, urlHandlersEntities);
            createAdminPermissionEntitiesForPermission(maintainUrlHandlers, urlHandlersEntities);
        }
        
        {
            AdminPermission viewRedirects = findPermissionById(-30L);
            AdminPermission maintainRedirects = findPermissionById(-31L);
            List<String> redirectEntities = Arrays.asList(
                    "org.broadleafcommerce.core.search.redirect.domain.SearchRedirect"
            );
            createAdminPermissionEntitiesForPermission(viewRedirects, redirectEntities);
            createAdminPermissionEntitiesForPermission(maintainRedirects, redirectEntities);
        }
        
        {
            AdminPermission viewFacets = findPermissionById(-32L);
            AdminPermission maintainFacets = findPermissionById(-33L);
            List<String> facetEntities = Arrays.asList(
                    "org.broadleafcommerce.core.search.domain.SearchFacet",
                    "org.broadleafcommerce.core.search.domain.Field",
                    "org.broadleafcommerce.core.search.domain.CategorySearchFacet",
                    "org.broadleafcommerce.core.search.domain.SearchFacetRange",
                    "org.broadleafcommerce.core.search.domain.CategoryExcludedSearchFacet",
                    "org.broadleafcommerce.core.search.domain.IndexField",
                    "org.broadleafcommerce.core.search.domain.IndexFieldType"
            );
            createAdminPermissionEntitiesForPermission(viewFacets, facetEntities);
            createAdminPermissionEntitiesForPermission(maintainFacets, facetEntities);
        }
        
        {
            AdminPermission viewCurrency = findPermissionById(-34L);
            AdminPermission maintainCurrency = findPermissionById(-35L);
            List<String> currencyEntities = Arrays.asList(
                    "org.broadleafcommerce.common.currency.domain.BroadleafCurrency"
            );
            createAdminPermissionEntitiesForPermission(viewCurrency, currencyEntities);
            createAdminPermissionEntitiesForPermission(maintainCurrency, currencyEntities);
        }
        
        {
            AdminPermission viewModuleConfiguration = findPermissionById(-36L);
            AdminPermission maintainModuleConfiguration= findPermissionById(-37L);
            List<String> moduleConfigurationEntities = Arrays.asList(
                    "org.broadleafcommerce.common.config.domain.ModuleConfiguration"
            );
            createAdminPermissionEntitiesForPermission(viewModuleConfiguration, moduleConfigurationEntities);
            createAdminPermissionEntitiesForPermission(maintainModuleConfiguration, moduleConfigurationEntities);
        }
        
        {
            AdminPermission viewEnumeration = findPermissionById(-38L);
            AdminPermission maintainEnumeration = findPermissionById(-39L);
            List<String> enumerationEntities = Arrays.asList(
                    "org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration",
                    "org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue"
            );
            createAdminPermissionEntitiesForPermission(viewEnumeration, enumerationEntities);
            createAdminPermissionEntitiesForPermission(maintainEnumeration, enumerationEntities);
        }
        
        {
            AdminPermission viewTranslation = findPermissionById(-40L);
            AdminPermission maintainTranslation = findPermissionById(-41L);
            List<String> translationEntities = Arrays.asList(
                    "org.broadleafcommerce.common.i18n.domain.Translation"
            );
            createAdminPermissionEntitiesForPermission(viewTranslation, translationEntities);
            createAdminPermissionEntitiesForPermission(maintainTranslation, translationEntities);
        }
        
        {
            AdminPermission viewSiteMap = findPermissionById(-42L);
            AdminPermission maintainSiteMap = findPermissionById(-43L);
            List<String> siteMapEntities = Arrays.asList(
                    "org.broadleafcommerce.common.sitemap.domain.SiteMapGeneratorConfiguration",
                    "org.broadleafcommerce.common.sitemap.domain.SiteMapURLEntry"
            );
            createAdminPermissionEntitiesForPermission(viewSiteMap, siteMapEntities);
            createAdminPermissionEntitiesForPermission(maintainSiteMap, siteMapEntities);
        }
        
        {
            AdminPermission viewProperties = findPermissionById(-44L);
            AdminPermission maintainProperties = findPermissionById(-45L);
            List<String> propertyEntities = Arrays.asList(
                    "org.broadleafcommerce.common.config.domain.SystemProperty"
            );
            createAdminPermissionEntitiesForPermission(viewProperties, propertyEntities);
            createAdminPermissionEntitiesForPermission(maintainProperties, propertyEntities);
        }
        
        {
            AdminPermission viewRoles = findPermissionById(-46L);
            AdminPermission maintainRoles = findPermissionById(-47L);
            List<String> roleEntities = Arrays.asList(
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminRole"
            );
            createAdminPermissionEntitiesForPermission(viewRoles, roleEntities);
            createAdminPermissionEntitiesForPermission(maintainRoles, roleEntities);
        }
        
        {
            AdminPermission viewPerms = findPermissionById(-48L);
            AdminPermission maintainPerms = findPermissionById(-49L);
            List<String> permEntities = Arrays.asList(
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminPermission",
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity"
            );
            createAdminPermissionEntitiesForPermission(viewPerms, permEntities);
            createAdminPermissionEntitiesForPermission(maintainPerms, permEntities);
        }
        
        {
            AdminPermission viewFieldDefs = findPermissionById(-50L);
            AdminPermission maintainFieldDefs= findPermissionById(-51L);
            List<String> fieldDefEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.field.domain.FieldDefinition"
            );
            createAdminPermissionEntitiesForPermission(viewFieldDefs, fieldDefEntities);
            createAdminPermissionEntitiesForPermission(maintainFieldDefs, fieldDefEntities);
        }
        
        {
            AdminPermission viewCatalog = findPermissionById(-52L);
            AdminPermission maintainCatalog = findPermissionById(-53L);
            List<String> catalogEntities = Arrays.asList(
                    "org.broadleafcommerce.common.site.domain.Catalog",
                    "org.broadleafcommerce.common.site.domain.Site"
            );
            createAdminPermissionEntitiesForPermission(viewCatalog, catalogEntities);
            createAdminPermissionEntitiesForPermission(maintainCatalog, catalogEntities);
        }
        
        {
            AdminPermission viewCountries = findPermissionById(-54L);
            AdminPermission maintainCountries = findPermissionById(-55L);
            List<String> countryEntities = Arrays.asList(
                    "org.broadleafcommerce.common.i18n.domain.ISOCountry"
            );
            createAdminPermissionEntitiesForPermission(viewCountries, countryEntities);
            createAdminPermissionEntitiesForPermission(maintainCountries, countryEntities);
        }
        
        {
            AdminPermission viewMessages = findPermissionById(-180L);
            AdminPermission maintainMessages = findPermissionById(-181L);
            List<String> messageEntities = Arrays.asList(
                    "org.broadleafcommerce.core.promotionMessage.domain.PromotionMessage"
            );
            createAdminPermissionEntitiesForPermission(viewMessages, messageEntities);
            createAdminPermissionEntitiesForPermission(maintainMessages, messageEntities);
        }
        
        {
            AdminPermission viewMessages = findPermissionById(-106L);
            AdminPermission maintainMessages = findPermissionById(-107L);
            List<String> messageEntities = Arrays.asList(
                    "org.broadleafcommerce.core.offer.domain.AdvancedOfferPromotionMessageXref"
            );
            createAdminPermissionEntitiesForPermission(viewMessages, messageEntities);
            createAdminPermissionEntitiesForPermission(maintainMessages, messageEntities);
        }
    }
    
    @Override
    protected void createAdminRoles() {
        createRole(-1L, "ROLE_ADMIN", "Admin Master Access");
        createRole(-2L, "ROLE_MERCHANDISE_MANAGER", "Merchandiser");
        createRole(-3L, "ROLE_PROMOTION_MANAGER", "Promotion Manager");
        createRole(-4L, "ROLE_CUSTOMER_SERVICE_REP", "CSR");
        createRole(-5L, "ROLE_CONTENT_EDITOR", "CMS Editor");
        createRole(-6L, "ROLE_CONTENT_APPROVER", "CMS Approver");
        createRole(-7L, "ROLE_CONTENT_DESIGNER", "CMS Designer");
        
        createRole(-8L, "ROLE_TEST_ROLE", "Test Role");
    }

    @Override
    protected void addPermissionsToRoles() {
        // Build ROLE_ADMIN
        addPermissionToRole(-1L, -101L);
        addPermissionToRole(-1L, -103L);
        addPermissionToRole(-1L, -105L);
        addPermissionToRole(-1L, -107L);
        addPermissionToRole(-1L, -109L);
        addPermissionToRole(-1L, -111L);
        addPermissionToRole(-1L, -115L);
        addPermissionToRole(-1L, -119L);
        addPermissionToRole(-1L, -121L);
        addPermissionToRole(-1L, -131L);
        addPermissionToRole(-1L, -141L);
        addPermissionToRole(-1L, -161L);
        addPermissionToRole(-1L, -181L);
        
        // Build ROLE_MERCHANDISE_MANAGER
        addPermissionToRole(-2L, -101L);
        addPermissionToRole(-2L, -103L);
        addPermissionToRole(-2L, -105L);
        addPermissionToRole(-2L, -111L);
        addPermissionToRole(-2L, -131L);
        
        // Build ROLE_PROMOTION_MANAGER
        addPermissionToRole(-3L, -107L);
        addPermissionToRole(-3L, -131L);
        
        // Build ROLE_CUSTOMER_SERVICE_REP
        addPermissionToRole(-4L, -119L);
        
        // Build ROLE_CONTENT_EDITOR
        addPermissionToRole(-5L, -109L);
        addPermissionToRole(-5L, -111L);
        addPermissionToRole(-5L, -131L);
        addPermissionToRole(-5L, -161L);
        
        // Build ROLE_CONTENT_APPROVER
        addPermissionToRole(-6L, -109L);
        addPermissionToRole(-6L, -111L);
        
        // Buid ROLE_TEST_ROLE
        addPermissionToRole(-8L, -101L);
    }

}