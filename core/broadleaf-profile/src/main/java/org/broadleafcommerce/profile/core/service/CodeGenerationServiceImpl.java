/*
 * #%L
 * BroadleafCommerce Profile
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
package org.broadleafcommerce.profile.core.service;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author Mark Curtis
 */
public class CodeGenerationServiceImpl implements CodeGenerationService {

    @Override
    public String generateCode(int length) {

        StringBuffer maxInt = new StringBuffer();
        for (int i=0; i<length; i++) {
            maxInt.append("9");

        }

        int max = Integer.parseInt(maxInt.toString());
        int random = RandomUtils.nextInt(0, max);

        StringBuffer codeStrBuffer = new StringBuffer(String.valueOf(random));
        for (int j=0; j<(length - String.valueOf(random).length()); j++) {
            codeStrBuffer.insert(0, "0");

        }

        return codeStrBuffer.toString();

    }
}
