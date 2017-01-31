/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
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
package org.broadleafcommerce.common.demo;

import org.broadleafcommerce.common.admin.condition.AdminExistsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jeff Fischer
 */
@Configuration("blOrderData")
@Conditional(ImportCondition.class)
public class OrderImportSQLConfig {

    @Bean
    @Conditional(DemoCondition.class)
    public AutoImportSql blOrderBasicData() {
        return new AutoImportSql(AutoImportPersistenceUnit.BL_PU,"config/bc/sql/demo/load_offer_data.sql", AutoImportStage.PRIMARY_BASIC_DATA);
    }

    @Bean
    @Conditional({DemoCondition.class, AdminExistsCondition.class})
    public AutoImportSql blOrderAdminData() {
        return new AutoImportSql(AutoImportPersistenceUnit.BL_PU,"config/bc/sql/demo/load_order_admin_permissions.sql,config/bc/sql/demo/load_order_admin_menu.sql", AutoImportStage.SECONDARY_FRAMEWORK_SECURITY);
    }

}
