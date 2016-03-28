/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
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
package org.broadleafcommerce.common.web.expression;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;


public class BroadleafVariableExpressionObjectFactory implements IExpressionObjectFactory {

    @Resource(name = "blVariableExpressions")
    protected List<BroadleafVariableExpression> expressions = new ArrayList<BroadleafVariableExpression>();
    
    @Override
    public Set<String> getAllExpressionObjectNames() {
        Set<String> expressionObjectNames = new HashSet<String>();
        for (BroadleafVariableExpression expression: expressions) {
            if (!(expression instanceof NullBroadleafVariableExpression)) {
                expressionObjectNames.add(expression.getName());
            }
        }
        return expressionObjectNames;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if (context instanceof IWebContext) {
            for (BroadleafVariableExpression expression: expressions) {
                if (expressionObjectName.equals(expression.getName())) {
                    return expression;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return true;
    }

}
