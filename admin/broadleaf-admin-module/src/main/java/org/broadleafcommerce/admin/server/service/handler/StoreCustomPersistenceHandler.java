package org.broadleafcommerce.admin.server.service.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.store.domain.Store;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by ReggieCole on 11/16/16.
 */
@Component("blStoreCustomPersistenceHandler")
public class StoreCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {

    private static final Log LOG = LogFactory.getLog(StoreCustomPersistenceHandler.class);


    @Override
    public Boolean canHandleAdd(PersistencePackage persistencePackage) {
        String ceilingName = persistencePackage.getCeilingEntityFullyQualifiedClassname();
        try {
            Class ceilingClass = Class.forName(ceilingName);
            return Store.class.isAssignableFrom(ceilingClass);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
        Entity entity = persistencePackage.getEntity();
        try {
            PersistencePerspective persistencePerspective = persistencePackage.getPersistencePerspective();
            Store adminInstance = (Store) Class.forName(entity.getType()[0]).newInstance();
            Map<String, FieldMetadata> adminProperties = helper.getSimpleMergedProperties(Store.class.getName(), persistencePerspective);
            Address blankAddress = new AddressImpl();
            blankAddress.setAddressLine1(" ");
            blankAddress.setCity(" ");
            adminInstance.setAddress(blankAddress);
            entity.setIsPreAdd(true);
            adminInstance = (Store) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

            adminInstance = dynamicEntityDao.merge(adminInstance);
            Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

            return adminEntity;
        } catch (Exception e) {
            LOG.error("Unable to execute persistence activity", e);
            throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
        }
    }
}
