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
package org.broadleafcommerce.core.order.domain;

import org.broadleafcommerce.common.presentation.AdminGroupPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminTabPresentation;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;

@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "CustomerImpl_baseCustomer",
    tabs = {
        @AdminTabPresentation(name = OrderCustomerAdminPresentation.TabName.General,
            order = OrderCustomerAdminPresentation.TabOrder.General,
            groups = {
                @AdminGroupPresentation(name = OrderCustomerAdminPresentation.GroupName.Customer,
                    order = OrderCustomerAdminPresentation.GroupOrder.Customer,
                    untitled = true),
                @AdminGroupPresentation(name = OrderCustomerAdminPresentation.GroupName.ContactInfo,
                    order = OrderCustomerAdminPresentation.GroupOrder.ContactInfo),
            }
        )
    }
)
public interface OrderCustomerAdminPresentation {
    
    public static class TabName {
        public static final String General = "General";
        public static final String Advanced = "OrderCustomerImpl_Advanced_Tab";
        public static final String PaymentMethods = "OrderCustomerImpl_Payment_Tab";
    }

    public static class TabOrder {
        public static final int General = 1000;
        public static final int Advanced = 5000;
    }

    public static class GroupName {
        public static final String Customer = "CustomerImpl_Customer";
        public static final String ContactInfo = "CustomerImpl_ContactInfo";
    }

    public static class GroupOrder {
        public static final int Customer = 1000;
        public static final int ContactInfo = 2000;
    }

    public static class FieldOrder {
        public static final int FIRST_NAME = 1000;
        public static final int LAST_NAME = 2000;
        public static final int EMAIL = 3000;
        public static final int EXTERNAL_ID = 4000;
    }
    
}
