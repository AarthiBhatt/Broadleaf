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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mark Curtis
 */
public class CodeGenerationServiceImplTest {

    @Test
    public void generateCode_length6() throws Exception {

        CodeGenerationService codeGenerationService = new CodeGenerationServiceImpl();

        for (int i=0; i<999999; i++) {
            String codeStr = codeGenerationService.generateCode(6);
            assertEquals("Length should always be 6", 6, codeStr.length());

        }
    }

}