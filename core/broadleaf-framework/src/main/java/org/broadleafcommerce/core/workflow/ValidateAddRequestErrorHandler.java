/*
 * #%L
 * BroadleafCommerce Integration
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
package org.broadleafcommerce.core.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.core.order.service.exception.RequiredAttributesNotProvidedException;
import org.springframework.stereotype.Component;

/**
 * Add an ErrorHandler that handles ValidateAddRequestActivity errors
 *
 * @author Jaci Eckert
 */
@Component("blValidateAddRequestErrorHandler")
public class ValidateAddRequestErrorHandler implements ErrorHandler {

    protected static final Log LOG = LogFactory.getLog(ValidateAddRequestErrorHandler.class);

    @Override
    public void handleError(ProcessContext context, Throwable th) throws WorkflowException {
        if(th instanceof RequiredAttributesNotProvidedException) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(th);
            }
            context.stopProcess();
            throw new WorkflowException(th);
        }
        else {
            throw new WorkflowException(th);
        }
    }

    @Override
    public void setBeanName(String name) {
        //do nothing
    }
}
