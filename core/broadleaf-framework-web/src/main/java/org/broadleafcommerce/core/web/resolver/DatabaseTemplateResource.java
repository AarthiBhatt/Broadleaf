package org.broadleafcommerce.core.web.resolver;

import org.broadleafcommerce.common.extension.ExtensionResultHolder;
import org.broadleafcommerce.common.extension.ExtensionResultStatusType;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;


public class DatabaseTemplateResource implements ITemplateResource {

    private DatabaseResourceResolverExtensionManager extensionManager;
    private final String characterEncoding;
    private final String templateName;
    private final Map<String, Object> templateResolutionAttributes;
    
    public DatabaseTemplateResource(DatabaseResourceResolverExtensionManager extensionManager, String characterEncoding, String templateName, Map<String, Object> templateResolutionAttributes) {

        // TODO Add asserts to make sure there is a connection to the database.
        this.extensionManager = extensionManager;
        this.characterEncoding = characterEncoding;
        this.templateName = templateName;
        this.templateResolutionAttributes = templateResolutionAttributes;
        
    }
    
    @Override
    public String getDescription() {
        return templateName;
    }

    @Override
    public String getBaseName() {
        //TODO strip everything but the base name.
        return templateName;
    }

    @Override
    public boolean exists() {
        ExtensionResultStatusType result = extensionManager.getProxy().resolveResource(new ExtensionResultHolder(), templateResolutionAttributes, templateName);
        return result == ExtensionResultStatusType.HANDLED;
    }

    @Override
    public Reader reader() throws IOException {
        ExtensionResultHolder erh = new ExtensionResultHolder();
        ExtensionResultStatusType result = extensionManager.getProxy().resolveResource(erh, templateResolutionAttributes, templateName);
        if (result == ExtensionResultStatusType.HANDLED) {
            InputStream inputStream = (InputStream) erh.getContextMap().get(DatabaseResourceResolverExtensionHandler.IS_KEY);
            if (!StringUtils.isEmptyOrWhitespace(this.characterEncoding)) {
                return new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), this.characterEncoding));
            }

            return new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
        }
        return null;
    }

    @Override
    public ITemplateResource relative(String relativeLocation) {
        // This method is supposed to be used to process other resources relative to the base
        // name of the resource. An example would be a starting resource of "/home/user/template/main.html"
        // where the "baseName" would be "main" and relative files would be "main.html" or "main.properties"
        // or "main.th.xml".
        // TODO Determine if we need to support this method for usage in the DatabaseTemplateResource.
        return null;
    }


}
