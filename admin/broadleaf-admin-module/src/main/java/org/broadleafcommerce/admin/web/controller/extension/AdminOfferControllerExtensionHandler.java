/*
 * #%L
 * BroadleafCommerce Admin Module
 * %%
 * Copyright (C) 2009 - 2015 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.broadleafcommerce.admin.web.controller.extension;

import org.broadleafcommerce.admin.web.controller.entity.AdminOfferController;
import org.broadleafcommerce.common.extension.ExtensionResultStatusType;
import org.broadleafcommerce.common.presentation.client.RuleBuilderDisplayType;
import org.broadleafcommerce.core.offer.domain.OfferAdminPresentation;
import org.broadleafcommerce.openadmin.web.controller.AbstractAdminAbstractControllerExtensionHandler;
import org.broadleafcommerce.openadmin.web.controller.AdminAbstractControllerExtensionManager;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;
import org.broadleafcommerce.openadmin.web.form.entity.Field;
import org.broadleafcommerce.openadmin.web.form.entity.FieldGroup;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Elbert Bautista (elbertbautista)
 */
@Component("blAdminOfferControllerExtensionHandler")
public class AdminOfferControllerExtensionHandler extends AbstractAdminAbstractControllerExtensionHandler {

    @Resource(name = "blAdminAbstractControllerExtensionManager")
    protected AdminAbstractControllerExtensionManager extensionManager;

    @PostConstruct
    public void init() {
        if (isEnabled()) {
            extensionManager.registerHandler(this);
        }
    }

    @Override
    public ExtensionResultStatusType setAdditionalModelAttributes(Model model, String sectionKey) {
        if (AdminOfferController.SECTION_KEY.equals(sectionKey)) {
            EntityForm form = (EntityForm) model.asMap().get("entityForm");

            if (form != null) {
                //UX Meta-Data to display the Rule Builders on the Offer Screen
                FieldGroup ruleConfigGroup = form.findGroup(OfferAdminPresentation.GroupName.RuleConfiguration);
                if (ruleConfigGroup != null) {
                    ruleConfigGroup.getGroupAttributes().put("additionalGroupClasses", "card");
                }

                // Qualifier and Target Item Builders
                Field qualField = form.findField("qualifyingItemCriteria");
                if (qualField != null) {
                    qualField.setDisplayType(RuleBuilderDisplayType.NORMAL.name());
                }
                Field fgField = form.findField("offerMatchRules---FULFILLMENT_GROUP");
                if (fgField != null) {
                    fgField.setDisplayType(RuleBuilderDisplayType.NORMAL.name());
                }
                Field tarField = form.findField("targetItemCriteria");
                if (tarField != null) {
                    tarField.setDisplayType(RuleBuilderDisplayType.NORMAL.name());
                }

                //Activity Range Builder
                Field timeField = form.findField("offerMatchRules---TIME");
                if (timeField != null) {
                    timeField.setDisplayType(RuleBuilderDisplayType.MODAL.name());
                }

                //Usage Builders
                Field custField = form.findField("offerMatchRules---CUSTOMER");
                if (custField != null) {
                    custField.setDisplayType(RuleBuilderDisplayType.MODAL.name());
                }

                Field orderField = form.findField("offerMatchRules---ORDER");
                if (orderField != null) {
                    orderField.setDisplayType(RuleBuilderDisplayType.MODAL.name());
                }
            }
        }

        return ExtensionResultStatusType.HANDLED_CONTINUE;
    }
}