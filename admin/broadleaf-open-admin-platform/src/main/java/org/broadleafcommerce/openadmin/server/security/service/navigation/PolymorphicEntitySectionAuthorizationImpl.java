/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
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
package org.broadleafcommerce.openadmin.server.security.service.navigation;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.util.dao.DynamicDaoHelper;
import org.broadleafcommerce.common.util.dao.DynamicDaoHelperImpl;
import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Jeff Fischer
 */
@Component("blPolymorphicEntityCheckSectionAuthorization")
public class PolymorphicEntitySectionAuthorizationImpl implements SectionAuthorization {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @PersistenceContext(unitName = "blEventPU")
    protected EntityManager emEvent;

    protected DynamicDaoHelper helper = new DynamicDaoHelperImpl();

    @Override
    public boolean isUserAuthorizedToViewSection(AdminUser adminUser, AdminSection section) {
        if (StringUtils.isBlank(section.getCeilingEntity())) {
            return true;
        }

        boolean response;
        try {
            //Only display this section if there are 1 or more entities relative to the ceiling
            //for this section that are qualified to be created by the admin
            Class<?> clazz = Class.forName(section.getCeilingEntity());
            response = helper.getAllPolymorphicEntitiesFromCeiling(clazz, helper.getSessionFactory((HibernateEntityManager) em), true, true).length > 0;
            if (!response) {
                response = helper.getAllPolymorphicEntitiesFromCeiling(clazz, helper.getSessionFactory((HibernateEntityManager) emEvent), true, true).length > 0;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
