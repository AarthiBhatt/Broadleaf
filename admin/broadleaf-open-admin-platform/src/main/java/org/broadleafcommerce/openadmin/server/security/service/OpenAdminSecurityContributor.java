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

import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.service.domain.AdminPermissionDTO;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("blOpenAdminSecurityContributor")
public class OpenAdminSecurityContributor extends AbstractAdminSecurityContributor implements AdminSecurityContributor {

    
    protected void createAdminModules() {
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-1,'Catalog','BLCMerchandising', 'blc-icon-catalog', 100);
        createModule("Catalog", ModuleKeys.CATALOG, "blc-icon-catalog", 100);
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-8,'Pricing','BLCPricing', 'fa fa-usd', 150);
        createModule("Pricing", ModuleKeys.PRICING, "fa fa-usd", 150);
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-2,'Content','BLCContentManagement', 'blc-icon-content', 200);
        createModule("Content", ModuleKeys.CONTENT, "blc-icon-content", 200);
        // INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-9,'Inventory','BLCInventory', 'blc-icon-inventory', 250);
        createModule("Inventory", ModuleKeys.INVENTORY, "blc-icon-inventory", 250);
        //INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-6,'Design','BLCDesign', 'blc-icon-design', 400);
        createModule("Design", ModuleKeys.DESIGN, "blc-icon-design", 400);
        //  INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-7,'Site Updates','BLCWorkflow', 'blc-icon-site-updates', 500);
        createModule("Site Updates", ModuleKeys.SITE_UPDATES, "blc-icon-site-updates", 500);
        //  INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-3,'Customer Care','BLCCustomerCare', 'blc-icon-customer-care', 550);
        createModule("Customer Care", ModuleKeys.CUSTOMER_CARE, "blc-icon-customer-care", 550);
        //  INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-4,'Security','BLCOpenAdmin', 'blc-icon-security', 600);
        createModule("Security", ModuleKeys.SECURITY, "blc-icon-security", 600);
        //  INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (-5,'Settings','BLCModuleConfiguration', 'blc-icon-settings', 700);
        createModule("Settings", ModuleKeys.SETTINGS, "blc-icon-settings", 700);
    }
    
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
    
    protected AdminSection createCategorySection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_CATEGORY", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_CATEGORY", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-1, 2000, -1, 'Category', 'Category', '/category', 'org.broadleafcommerce.core.catalog.domain.Category');
        AdminSection categorySection = createSection("Category", SectionKeys.CATEGORY, "/category", "org.broadleafcommerce.core.catalog.domain.Category", ModuleKeys.CATALOG, 2000, permissions);
        return categorySection;
    }
    
    protected AdminSection createProductSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_PRODUCT", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_PRODUCT", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-2, 3000, -1, 'Product', 'Product', '/product', 'org.broadleafcommerce.core.catalog.domain.Product');
        return createSection("Product", SectionKeys.PRODUCT, "/product", "org.broadleafcommerce.core.catalog.domain.Product", ModuleKeys.CATALOG, 3000, permissions);
    }
    
    protected AdminSection createProductOptionsSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_PRODUCTOPTIONS", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_PRODUCTOPTIONS", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-3, 5000, -1, 'Product Options', 'ProductOptions', '/product-options', 'org.broadleafcommerce.core.catalog.domain.ProductOption');
        return createSection("Product Options", SectionKeys.PRODUCT_OPTIONS, "/product-options", "org.broadleafcommerce.core.catalog.domain.ProductOption", ModuleKeys.CATALOG, 5000, permissions);
    }
    
    protected AdminSection createOfferSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_OFFER", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_OFFER", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-4, 1000, -8, 'Offer', 'Offer', '/offer', 'org.broadleafcommerce.core.offer.domain.Offer');
        return createSection("Offer", SectionKeys.OFFER, "/offer", "org.broadleafcommerce.core.offer.domain.Offer", ModuleKeys.PRICING, 1000, permissions);
    }
    
    protected AdminSection createPagesSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_PAGE", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_PAGE", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-5, 2000, -2, 'Pages', 'Pages', '/pages', 'org.broadleafcommerce.cms.page.domain.Page');
        return createSection("Pages", SectionKeys.PAGES, "/pages", "org.broadleafcommerce.cms.page.domain.Page", ModuleKeys.CONTENT, 2000, permissions);
    }
    
    protected AdminSection createAssetsSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_ASSET", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_ASSET", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-6, 4000, -2, 'Assets', 'Assets', '/assets', 'org.broadleafcommerce.cms.file.domain.StaticAsset');
        return createSection("Assets", SectionKeys.ASSETS, "/assets", "org.broadleafcommerce.cms.file.domain.StaticAsset", ModuleKeys.CONTENT, 4000, permissions);
    }
    
    protected AdminSection createRedirectUrlSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_URLREDIRECT", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_URLREDIRECT", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-8, 7000, -2, 'Redirect URL', 'RedirectURL', '/redirect-url', 'org.broadleafcommerce.cms.url.domain.URLHandler');
        return createSection("Redirect URL", SectionKeys.REDIRECT_URL, "/redirect-url", "org.broadleafcommerce.cms.url.domain.URLHandler", ModuleKeys.CONTENT, 7000, permissions);
    }
    
    protected AdminSection createCustomerSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_CUSTOMER", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_CUSTOMER", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-10, 1000, -3,'Customer', 'Customer', '/customer', 'org.broadleafcommerce.profile.core.domain.Customer');
        return createSection("Customer", SectionKeys.CUSTOMER, "/customer", "org.broadleafcommerce.profile.core.domain.Customer", ModuleKeys.CUSTOMER_CARE, 1000, permissions);
    }
    
    protected AdminSection createUserManagementSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_USER", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_USER", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-11, 2000, -5, 'User Management', 'UserManagement', '/user-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminUser');
        return createSection("User Management", SectionKeys.USER_MANAGEMENT, "/user-management", "org.broadleafcommerce.openadmin.server.security.domain.AdminUser", ModuleKeys.SETTINGS, 2000, permissions);
    }
    
    protected AdminSection createRoleManagementSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_ROLE_VIEW", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_ROLE_ALL", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-12, 3000, -5, 'Role Management', 'RoleManagement', '/role-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminRole');
        return createSection("Role Management", SectionKeys.ROLE_MANAGEMENT, "/role-management", "org.broadleafcommerce.openadmin.server.security.domain.AdminRole", ModuleKeys.SETTINGS, 3000, permissions);
    }
    
    protected AdminSection createConfigurationManagementSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_MODULECONFIGURATION", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_MODULECONFIGURATION", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-13, 10000, -5, 'Configuration Management', 'ConfigurationManagement', '/configuration-management', 'org.broadleafcommerce.common.config.domain.ModuleConfiguration');
        return createSection("Configuration Management", SectionKeys.CONFIGURATION_MANAGEMENT, "/configuration-management", "org.broadleafcommerce.common.config.domain.ModuleConfiguration", ModuleKeys.SETTINGS, 10000, permissions);
    }
    
    protected AdminSection createPermissionManagementSection() {
        ArrayList<AdminPermissionDTO> permissions = new ArrayList<>();
        permissions.add(createPermission("PERMISSION_PERM_VIEW", PermissionType.READ));
        permissions.add(createPermission("PERMISSION_PERM_ALL", PermissionType.ALL));
        // INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (-17, 11000, -5, 'Permission Management', 'PermissionManagement', '/permission-management', 'org.broadleafcommerce.openadmin.server.security.domain.AdminPermission');
        return createSection("Permission Management", SectionKeys.PERMISSION_MANAGEMENT, "/permission-management", "org.broadleafcommerce.openadmin.server.security.domain.AdminPermission", ModuleKeys.SETTINGS, 11000, permissions);
    }
    
    protected void createAdminPermissionEntities() {
        {
            AdminPermissionDTO viewCategory = createPermission("PERMISSION_READ_CATEGORY", PermissionType.READ);
            AdminPermissionDTO maintainCategory = createPermission("PERMISSION_ALL_CATEGORY", PermissionType.ALL);
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
            AdminPermissionDTO viewProduct = createPermission("PERMISSION_READ_PRODUCT", PermissionType.READ);
            AdminPermissionDTO maintainProduct = createPermission("PERMISSION_ALL_PRODUCT", PermissionType.ALL);
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
            AdminPermissionDTO viewProductOption = createPermission("PERMISSION_READ_PRODUCT_OPTION", PermissionType.READ);
            AdminPermissionDTO maintainProductOption = createPermission("PERMISSION_ALL_PRODUCT_OPTION", PermissionType.ALL);
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
            AdminPermissionDTO viewSku = createPermission("PERMISSION_READ_SKU", PermissionType.READ);
            AdminPermissionDTO maintainSku = createPermission("PERMISSION_ALL_SKU", PermissionType.ALL);
            List<String> skuEntities = Arrays.asList(
                    "org.broadleafcommerce.core.catalog.domain.Sku"
            );
            createAdminPermissionEntitiesForPermission(viewSku, skuEntities);
            createAdminPermissionEntitiesForPermission(maintainSku, skuEntities);
        }
        
        {
            AdminPermissionDTO viewOffer = createPermission("PERMISSION_READ_PROMOTION", PermissionType.READ);
            AdminPermissionDTO maintainOffer = createPermission("PERMISSION_ALL_PROMOTION", PermissionType.ALL);
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
            AdminPermissionDTO viewOrder = createPermission("PERMISSION_READ_ORDER", PermissionType.READ);
            AdminPermissionDTO maintainOrder = createPermission("PERMISSION_ALL_ORDER", PermissionType.ALL);
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
            AdminPermissionDTO viewFg = createPermission("PERMISSION_READ_FULFILLMENT_GROUP", PermissionType.READ);
            AdminPermissionDTO maintainFg = createPermission("PERMISSION_ALL_FULFILLMENT_GROUP", PermissionType.ALL);
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
            AdminPermissionDTO viewOi = createPermission("PERMISSION_READ_ORDER_ITEM", PermissionType.READ);
            AdminPermissionDTO maintainOi = createPermission("PERMISSION_ALL_ORDER_ITEM", PermissionType.ALL);
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
            AdminPermissionDTO viewCustomer = createPermission("PERMISSION_READ_CUSTOMER", PermissionType.READ);
            AdminPermissionDTO maintainCustomer = createPermission("PERMISSION_ALL_CUSTOMER", PermissionType.ALL);
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
            AdminPermissionDTO viewPage = createPermission("PERMISSION_READ_PAGE", PermissionType.READ);
            AdminPermissionDTO maintainPage = createPermission("PERMISSION_ALL_PAGE", PermissionType.ALL);
            List<String> pageEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.page.domain.Page",
                    "org.broadleafcommerce.cms.page.domain.PageItemCriteria",
                    "org.broadleafcommerce.common.locale.domain.Locale"
            );
            createAdminPermissionEntitiesForPermission(viewPage, pageEntities);
            createAdminPermissionEntitiesForPermission(maintainPage, pageEntities);
        }
        
        {
            AdminPermissionDTO viewPageTemplate = createPermission("PERMISSION_READ_PAGE_TEMPLATE", PermissionType.READ);
            List<String> pageTemplateEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.page.domain.PageTemplate"
            );
            createAdminPermissionEntitiesForPermission(viewPageTemplate, pageTemplateEntities);
        }
        
        {
            AdminPermissionDTO viewAsset = createPermission("PERMISSION_READ_ASSET", PermissionType.READ);
            AdminPermissionDTO maintainAsset = createPermission("PERMISSION_ALL_ASSET", PermissionType.ALL);
            List<String> assetEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.file.domain.StaticAsset",
                    "org.broadleafcommerce.cms.file.domain.StaticAssetFolder"
            );
            createAdminPermissionEntitiesForPermission(viewAsset, assetEntities);
            createAdminPermissionEntitiesForPermission(maintainAsset, assetEntities);
        }
        
        {
            AdminPermissionDTO viewUser = createPermission("PERMISSION_READ_ADMIN_USER", PermissionType.READ);
            AdminPermissionDTO maintainUser = createPermission("PERMISSION_ALL_ADMIN_USER", PermissionType.ALL);
            List<String> userEntities = Arrays.asList(
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminUser"
            );
            createAdminPermissionEntitiesForPermission(viewUser, userEntities);
            createAdminPermissionEntitiesForPermission(maintainUser, userEntities);
        }
        
        {
            AdminPermissionDTO viewUrlHandlers = createPermission("PERMISSION_READ_URLHANDLER", PermissionType.READ);
            AdminPermissionDTO maintainUrlHandlers = createPermission("PERMISSION_ALL_URLHANDLER", PermissionType.ALL);
            List<String> urlHandlersEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.url.domain.URLHandler",
                    "org.broadleafcommerce.common.locale.domain.Locale"
            );
            createAdminPermissionEntitiesForPermission(viewUrlHandlers, urlHandlersEntities);
            createAdminPermissionEntitiesForPermission(maintainUrlHandlers, urlHandlersEntities);
        }
        
        {
            AdminPermissionDTO viewRedirects = createPermission("PERMISSION_READ_SEARCHREDIRECT", PermissionType.READ);
            AdminPermissionDTO maintainRedirects = createPermission("PERMISSION_ALL_SEARCHREDIRECT", PermissionType.ALL);
            List<String> redirectEntities = Arrays.asList(
                    "org.broadleafcommerce.core.search.redirect.domain.SearchRedirect"
            );
            createAdminPermissionEntitiesForPermission(viewRedirects, redirectEntities);
            createAdminPermissionEntitiesForPermission(maintainRedirects, redirectEntities);
        }
        
        {
            AdminPermissionDTO viewFacets = createPermission("PERMISSION_READ_SEARCHFACET", PermissionType.READ);
            AdminPermissionDTO maintainFacets = createPermission("PERMISSION_ALL_SEARCHFACET", PermissionType.ALL);
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
            AdminPermissionDTO viewCurrency = createPermission("PERMISSION_READ_CURRENCY", PermissionType.READ);
            AdminPermissionDTO maintainCurrency = createPermission("PERMISSION_ALL_CURRENCY", PermissionType.ALL);
            List<String> currencyEntities = Arrays.asList(
                    "org.broadleafcommerce.common.currency.domain.BroadleafCurrency"
            );
            createAdminPermissionEntitiesForPermission(viewCurrency, currencyEntities);
            createAdminPermissionEntitiesForPermission(maintainCurrency, currencyEntities);
        }
        
        {
            AdminPermissionDTO viewModuleConfiguration = createPermission("PERMISSION_READ_MODULECONFIGURATION", PermissionType.READ);
            AdminPermissionDTO maintainModuleConfiguration= createPermission("PERMISSION_ALL_MODULECONFIGURATION", PermissionType.ALL);
            List<String> moduleConfigurationEntities = Arrays.asList(
                    "org.broadleafcommerce.common.config.domain.ModuleConfiguration"
            );
            createAdminPermissionEntitiesForPermission(viewModuleConfiguration, moduleConfigurationEntities);
            createAdminPermissionEntitiesForPermission(maintainModuleConfiguration, moduleConfigurationEntities);
        }
        
        {
            AdminPermissionDTO viewEnumeration = createPermission("PERMISSION_READ_ENUMERATION", PermissionType.READ);
            AdminPermissionDTO maintainEnumeration = createPermission("PERMISSION_ALL_ENUMERATION", PermissionType.ALL);
            List<String> enumerationEntities = Arrays.asList(
                    "org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumeration",
                    "org.broadleafcommerce.common.enumeration.domain.DataDrivenEnumerationValue"
            );
            createAdminPermissionEntitiesForPermission(viewEnumeration, enumerationEntities);
            createAdminPermissionEntitiesForPermission(maintainEnumeration, enumerationEntities);
        }
        
        {
            AdminPermissionDTO viewTranslation = createPermission("PERMISSION_READ_TRANSLATION", PermissionType.READ);
            AdminPermissionDTO maintainTranslation = createPermission("PERMISSION_ALL_TRANSLATION", PermissionType.ALL);
            List<String> translationEntities = Arrays.asList(
                    "org.broadleafcommerce.common.i18n.domain.Translation"
            );
            createAdminPermissionEntitiesForPermission(viewTranslation, translationEntities);
            createAdminPermissionEntitiesForPermission(maintainTranslation, translationEntities);
        }
        
        {
            AdminPermissionDTO viewSiteMap = createPermission("PERMISSION_READ_SITE_MAP_GEN_CONFIG", PermissionType.READ);
            AdminPermissionDTO maintainSiteMap = createPermission("PERMISSION_ALL_SITE_MAP_GEN_CONFIG", PermissionType.ALL);
            List<String> siteMapEntities = Arrays.asList(
                    "org.broadleafcommerce.common.sitemap.domain.SiteMapGeneratorConfiguration",
                    "org.broadleafcommerce.common.sitemap.domain.SiteMapURLEntry"
            );
            createAdminPermissionEntitiesForPermission(viewSiteMap, siteMapEntities);
            createAdminPermissionEntitiesForPermission(maintainSiteMap, siteMapEntities);
        }
        
        {
            AdminPermissionDTO viewProperties = createPermission("PERMISSION_READ_SYSTEM_PROPERTY", PermissionType.READ);
            AdminPermissionDTO maintainProperties = createPermission("PERMISSION_ALL_SYSTEM_PROPERTY", PermissionType.ALL);
            List<String> propertyEntities = Arrays.asList(
                    "org.broadleafcommerce.common.config.domain.SystemProperty"
            );
            createAdminPermissionEntitiesForPermission(viewProperties, propertyEntities);
            createAdminPermissionEntitiesForPermission(maintainProperties, propertyEntities);
        }
        
        {
            AdminPermissionDTO viewRoles = createPermission("PERMISSION_READ_ADMIN_ROLES", PermissionType.READ);
            AdminPermissionDTO maintainRoles = createPermission("PERMISSION_ALL_ADMIN_ROLES", PermissionType.ALL);
            List<String> roleEntities = Arrays.asList(
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminRole"
            );
            createAdminPermissionEntitiesForPermission(viewRoles, roleEntities);
            createAdminPermissionEntitiesForPermission(maintainRoles, roleEntities);
        }
        
        {
            AdminPermissionDTO viewPerms = createPermission("PERMISSION_READ_ADMIN_PERMS", PermissionType.READ);
            AdminPermissionDTO maintainPerms = createPermission("PERMISSION_ALL_ADMIN_PERMS", PermissionType.ALL);
            List<String> permEntities = Arrays.asList(
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminPermission",
                    "org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity"
            );
            createAdminPermissionEntitiesForPermission(viewPerms, permEntities);
            createAdminPermissionEntitiesForPermission(maintainPerms, permEntities);
        }
        
        {
            AdminPermissionDTO viewFieldDefs = createPermission("PERMISSION_READ_FIELD_DEFS", PermissionType.READ);
            AdminPermissionDTO maintainFieldDefs= createPermission("PERMISSION_ALL_FIELD_DEFS", PermissionType.ALL);
            List<String> fieldDefEntities = Arrays.asList(
                    "org.broadleafcommerce.cms.field.domain.FieldDefinition"
            );
            createAdminPermissionEntitiesForPermission(viewFieldDefs, fieldDefEntities);
            createAdminPermissionEntitiesForPermission(maintainFieldDefs, fieldDefEntities);
        }
        
        {
            AdminPermissionDTO viewCatalog = createPermission("PERMISSION_READ_CATALOG_PERMS", PermissionType.READ);
            AdminPermissionDTO maintainCatalog = createPermission("PERMISSION_ALL_CATALOG_PERMS", PermissionType.ALL);
            List<String> catalogEntities = Arrays.asList(
                    "org.broadleafcommerce.common.site.domain.Catalog",
                    "org.broadleafcommerce.common.site.domain.Site"
            );
            createAdminPermissionEntitiesForPermission(viewCatalog, catalogEntities);
            createAdminPermissionEntitiesForPermission(maintainCatalog, catalogEntities);
        }
        
        {
            AdminPermissionDTO viewCountries = createPermission("PERMISSION_READ_ISO_COUNTRIES", PermissionType.READ);
            AdminPermissionDTO maintainCountries = createPermission("PERMISSION_ALL_ISO_COUNTRIES", PermissionType.ALL);
            List<String> countryEntities = Arrays.asList(
                    "org.broadleafcommerce.common.i18n.domain.ISOCountry"
            );
            createAdminPermissionEntitiesForPermission(viewCountries, countryEntities);
            createAdminPermissionEntitiesForPermission(maintainCountries, countryEntities);
        }
        
        {
            AdminPermissionDTO viewMessages = createPermission("PERMISSION_PROMOTION_MESSAGE", PermissionType.READ);
            AdminPermissionDTO maintainMessages = createPermission("PERMISSION_PROMOTION_MESSAGE", PermissionType.ALL);
            List<String> messageEntities = Arrays.asList(
                    "org.broadleafcommerce.core.promotionMessage.domain.PromotionMessage"
            );
            createAdminPermissionEntitiesForPermission(viewMessages, messageEntities);
            createAdminPermissionEntitiesForPermission(maintainMessages, messageEntities);
        }
        
        {
            AdminPermissionDTO viewMessages = createPermission("PERMISSION_OFFER", PermissionType.READ);
            AdminPermissionDTO maintainMessages = createPermission("PERMISSION_OFFER", PermissionType.ALL);
            List<String> messageEntities = Arrays.asList(
                    "org.broadleafcommerce.core.offer.domain.AdvancedOfferPromotionMessageXref"
            );
            createAdminPermissionEntitiesForPermission(viewMessages, messageEntities);
            createAdminPermissionEntitiesForPermission(maintainMessages, messageEntities);
        }
    }
}