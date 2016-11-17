/*
 * #%L
 * BroadleafCommerce Admin Module
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
package org.broadleafcommerce.admin.web.controller.entity;

import org.broadleafcommerce.core.store.domain.StoreAdminPresentation;
import org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;
import org.broadleafcommerce.openadmin.web.form.entity.Field;
import org.broadleafcommerce.openadmin.web.form.entity.FieldGroup;
import org.broadleafcommerce.openadmin.web.form.entity.Tab;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;

/**
 * Created by ReggieCole on 11/16/16.
 */
@Controller("blAdminStoreController")
@RequestMapping("/" + AdminStoreController.SECTION_KEY)
public class AdminStoreController extends AdminBasicEntityController {

    public static final String SECTION_KEY = "store";


    @Override
    protected String getSectionKey(Map<String, String> pathVars) {
        //allow external links to work for ToOne items
        if (super.getSectionKey(pathVars) != null) {
            return super.getSectionKey(pathVars);
        }
        return SECTION_KEY;
    }


    @Override
    protected void modifyAddEntityForm(EntityForm ef, Map<String, String> pathVars) {
        Tab generalTab = ef.findTab(StoreAdminPresentation.TabName.Location);
        if (generalTab != null) {
            FieldGroup addressFieldGroup = generalTab.findGroupByKey("AddressImpl_Address");
            Set<Field> fields = addressFieldGroup.getFields();
            for (Field field : fields) {
                addressFieldGroup.removeField(field);
            }
            FieldGroup phoneFieldGroup = generalTab.findGroupByKey("PhoneImpl_Phone");
            phoneFieldGroup.setIsVisible(false);
            addressFieldGroup.setIsVisible(false);
        }

    }

}
