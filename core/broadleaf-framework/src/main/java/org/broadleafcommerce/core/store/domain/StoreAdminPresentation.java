/*
 * #%L
 * BroadleafCommerce Framework
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
package org.broadleafcommerce.core.store.domain;

import org.broadleafcommerce.common.presentation.AdminGroupPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminTabPresentation;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;

/**
 * Created by ReggieCole on 11/16/16.
 */
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "StoreImpl_baseStore",
        tabs = {
                @AdminTabPresentation(name = StoreAdminPresentation.TabName.General,
                        order = StoreAdminPresentation.TabOrder.General
                ),
                @AdminTabPresentation(name = StoreAdminPresentation.TabName.Location,
                        order = StoreAdminPresentation.TabOrder.Location,
                        groups = {
                                @AdminGroupPresentation(name = StoreAdminPresentation.GroupName.Geocoding,
                                        order = StoreAdminPresentation.GroupOrder.Geocoding)
                        }
                )
        }
)

public interface StoreAdminPresentation {

    public static class TabName {
        public static final String General = "General";
        public static final String Location = "StoreImpl_Store_Location";
    }

    public static class TabOrder {
        public static final int General = 1000;
        public static final int Location = 7000;
    }

    public static class GroupName {
        public static final String Geocoding = "StoreImpl_Store_Geocoding";
    }

    public static class GroupOrder {
        public static final int Geocoding = 1000;
    }

    public static class FieldOrder {
        public static final int NAME = 1000;
        public static final int LATITUDE = 9000;
        public static final int LONGITUDE = 10000;
    }

}
