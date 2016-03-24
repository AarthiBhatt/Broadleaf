/*
 * #%L
 * BroadleafCommerce Common Libraries
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
package org.broadleafcommerce.common.web.dialect;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;


/**
 * @author apazzolini
 * 
 * Wrapper class around Thymeleaf's AbstractElementProcessor that facilitates adding Objects
 * to the current evaluation context (model) for processing in the remainder of the page.
 *
 */
public abstract class AbstractModelVariableModifierProcessor extends AbstractAttributeModelProcessor {

    public static final String DIALECT_PREFIX = "blc";
    
    protected AbstractModelVariableModifierProcessor(TemplateMode templateMode, String elementName, boolean prefixElementName, String attributeName, boolean prefixAttributeName, int precedence, boolean removeAttribute) {
        super(templateMode, DIALECT_PREFIX, elementName, prefixElementName, attributeName, prefixAttributeName, precedence, removeAttribute);
    }
    
    @Override
    protected void doProcess(
            final ITemplateContext context,
            final IModel model,
            final AttributeName attributeName,
            final String attributeValue,
            final IElementModelStructureHandler structureHandler) {
        
    }
    
//    public AbstractModelVariableModifierProcessor(String elementName) {
//        super(elementName);
//    }
//
//    @Override
//    public int getPrecedence() {
//        return 1000;
//    }
//
//    /**
//     * This method will handle calling the modifyModelAttributes abstract method and return
//     * an "OK" processor result
//     */
//    @Override
//    protected ProcessorResult processElement(final Arguments arguments, final Element element) {
//        modifyModelAttributes(arguments, element);
//        
//        // Remove the tag from the DOM
//        final NestableNode parent = element.getParent();
//        parent.removeChild(element);
//        
//        return ProcessorResult.OK;
//    }
//    
//    /**
//     * Helper method to add a value to the expression evaluation root (model) Map
//     * @param key the key to add to the model
//     * @param value the value represented by the key
//     */
//    @SuppressWarnings("unchecked")
//    protected void addToModel(Arguments arguments, String key, Object value) {
//        ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, value);
//    }
//    
//    @SuppressWarnings("unchecked")
//    protected <T> void addCollectionToExistingSet(Arguments arguments, String key, Collection<T> value) {
//        Set<T> items = (Set<T>) ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).get(key);
//        if (items == null) {
//            items = new HashSet<T>();
//            ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, items);
//        }
//        items.addAll(value);
//    }
//
//    @SuppressWarnings("unchecked")
//    protected <T> void addItemToExistingSet(Arguments arguments, String key, Object value) {
//        Set<T> items = (Set<T>) ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).get(key);
//        if (items == null) {
//            items = new HashSet<T>();                         
//            ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, items);
//        }
//        items.add((T) value);
//    }
//    
//    /**
//     * This method must be overriding by a processor that wishes to modify the model. It will
//     * be called by this abstract processor in the correct precendence in the evaluation chain.
//     * @param arguments
//     * @param element
//     */
//    protected abstract void modifyModelAttributes(Arguments arguments, Element element);
}
