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
package org.broadleafcommerce.openadmin.web.processor;

import org.springframework.stereotype.Component;

/**
 * @author Elbert Bautista (elbertbautista)
 */
@Component("blAdminFieldBuilderProcessor")
public class AdminFieldBuilderProcessor /*extends AbstractLocalVariableDefinitionElementProcessor*/ {

//    @Resource(name = "blRuleBuilderFieldServiceFactory")
//    protected RuleBuilderFieldServiceFactory ruleBuilderFieldServiceFactory;
//
//    /**
//     * Sets the name of this processor to be used in Thymeleaf template
//     */
//    public AdminFieldBuilderProcessor() {
//        super("admin_field_builder");
//    }
//
//    @Override
//    public int getPrecedence() {
//        return 100;
//    }
//
//    @Override
//    protected Map<String, Object> getNewLocalVariables(Arguments arguments, Element element) {
//        FieldWrapper fieldWrapper = new FieldWrapper();
//        
//        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
//                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue("fieldBuilder"));
//        String fieldBuilder = (String) expression.execute(arguments.getConfiguration(), arguments);
//
//        if (fieldBuilder != null) {
//            RuleBuilderFieldService ruleBuilderFieldService = ruleBuilderFieldServiceFactory.createInstance(fieldBuilder);
//            if (ruleBuilderFieldService != null) {
//                fieldWrapper = ruleBuilderFieldService.buildFields();
//            }
//        }
//        
//        Map<String, Object> newVars = new HashMap<String, Object>();
//        newVars.put("fieldWrapper", fieldWrapper);
//        return newVars;
//    }
//
//    @Override
//    protected boolean removeHostElement(Arguments arguments, Element element) {
//        return false;
//    }
}
