/*
 * #%L
 * BroadleafCommerce Open Admin Platform
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
package org.broadleafcommerce.openadmin.spec

import org.broadleafcommerce.common.util.BLCMessageUtils
import org.broadleafcommerce.openadmin.dto.GroupMetadata
import org.broadleafcommerce.openadmin.dto.TabMetadata
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm
import org.broadleafcommerce.openadmin.web.form.entity.FieldGroup
import org.broadleafcommerce.openadmin.web.form.entity.Tab
import org.broadleafcommerce.openadmin.web.service.FormBuilderService
import org.broadleafcommerce.openadmin.web.service.FormBuilderServiceImpl
import org.springframework.context.ApplicationContext
import org.springframework.context.MessageSource

import spock.lang.Specification

/**
 * @author Chad Harchar (charchar)
 */
class FormBuilderServiceSpec extends Specification {

    def "Test separate Tabs with merged and not merged groups"() {
        setup:

        MessageSource mockMessageSource = Mock()
        mockMessageSource.getMessage("TestTabName", _, _) >> "Friendly Tab Name"
        mockMessageSource.getMessage("Friendly Tab Name", _, _) >> "Friendly Tab Name"

        ApplicationContext applicationContext = Mock()
        applicationContext.getBean("messageSource") >> mockMessageSource

        BLCMessageUtils blcMessageUtils = new BLCMessageUtils()
        blcMessageUtils.setApplicationContext(applicationContext)


        FormBuilderService formBuilderService = new FormBuilderServiceImpl()
        EntityForm entityForm = new EntityForm()

        TabMetadata tabMetadata1 = new TabMetadata()
        tabMetadata1.setTabName("TestTabName")


        GroupMetadata groupMetadata1 = new GroupMetadata()
        groupMetadata1.setGroupName("TestGroupName")

        GroupMetadata groupMetadata2 = new GroupMetadata()
        groupMetadata2.setGroupName("TestGroupName2")


        Map<String, GroupMetadata> groupMetadataMap1 = new HashMap<>()
        groupMetadataMap1.put("testGroup1", groupMetadata1)
        groupMetadataMap1.put("testGroup2", groupMetadata2)
        tabMetadata1.setGroupMetadata(groupMetadataMap1)


        TabMetadata tabMetadata2 = new TabMetadata()
        tabMetadata2.setTabName("OtherTabName")

        GroupMetadata groupMetadata3 = new GroupMetadata()
        groupMetadata3.setGroupName("OtherTabGroup")

        GroupMetadata groupMetadata4 = new GroupMetadata()
        groupMetadata4.setGroupName("OtherTabGroup")

        Map<String, GroupMetadata> groupMetadataMap2 = new HashMap<>()
        groupMetadataMap2.put("testGroup3", groupMetadata3)
        groupMetadataMap2.put("testGroup4", groupMetadata4)
        tabMetadata2.setGroupMetadata(groupMetadataMap2)


        Map<String, TabMetadata> tabMetadataMap = new HashMap<>()
        tabMetadataMap.put("tab1", tabMetadata1)
        tabMetadataMap.put("tab2", tabMetadata2)

        when: "I execute setEntityFormTabsAndGroups"
        formBuilderService.setEntityFormTabsAndGroups(entityForm, tabMetadataMap)

        then: "No exceptions should be thrown"
        notThrown(Exception)
        entityForm.getTabs().size() == 2
        for (Tab tab : entityForm.getTabs()) {
            if (tab.getTitle().equals("Friendly Tab Name") || tab.getKey().equals("TestTabName")) {
                assert tab.getFieldGroups().size() == 2
            } else if (tab.getKey().equals("OtherTabName")) {
                assert tab.getFieldGroups().size() == 1
            } else {
                assert false
            }
        }
    }

    def "Test merged tabs with merged and not merged groups"() {
        setup:

        MessageSource mockMessageSource = Mock()
        mockMessageSource.getMessage("TestTabName", _, _) >> "Friendly Tab Name"
        mockMessageSource.getMessage("Friendly Tab Name", _, _) >> "Friendly Tab Name"

        ApplicationContext applicationContext = Mock()
        applicationContext.getBean("messageSource") >> mockMessageSource

        BLCMessageUtils blcMessageUtils = new BLCMessageUtils()
        blcMessageUtils.setApplicationContext(applicationContext)


        FormBuilderService formBuilderService = new FormBuilderServiceImpl()
        EntityForm entityForm = new EntityForm()

        TabMetadata tabMetadata1 = new TabMetadata()
        tabMetadata1.setTabName("TestTabName")


        GroupMetadata groupMetadata1 = new GroupMetadata()
        groupMetadata1.setGroupName("TestGroupName")

        GroupMetadata groupMetadata2 = new GroupMetadata()
        groupMetadata2.setGroupName("OtherTabGroup")


        Map<String, GroupMetadata> groupMetadataMap1 = new HashMap<>()
        groupMetadataMap1.put("testGroup1", groupMetadata1)
        groupMetadataMap1.put("testGroup2", groupMetadata2)
        tabMetadata1.setGroupMetadata(groupMetadataMap1)


        TabMetadata tabMetadata2 = new TabMetadata()
        tabMetadata2.setTabName("Friendly Tab Name")

        GroupMetadata groupMetadata3 = new GroupMetadata()
        groupMetadata3.setGroupName("OtherTabGroup")

        GroupMetadata groupMetadata4 = new GroupMetadata()
        groupMetadata4.setGroupName("TestGroupName2")

        Map<String, GroupMetadata> groupMetadataMap2 = new HashMap<>()
        groupMetadataMap2.put("testGroup3", groupMetadata3)
        groupMetadataMap2.put("testGroup4", groupMetadata4)
        tabMetadata2.setGroupMetadata(groupMetadataMap2)


        Map<String, TabMetadata> tabMetadataMap = new HashMap<>()
        tabMetadataMap.put("tab1", tabMetadata1)
        tabMetadataMap.put("tab2", tabMetadata2)

        when: "I execute setEntityFormTabsAndGroups"
        formBuilderService.setEntityFormTabsAndGroups(entityForm, tabMetadataMap)

        then: "No exceptions should be thrown"
        notThrown(Exception)
        entityForm.getTabs().size() == 1
        for (Tab tab : entityForm.getTabs()) {
            if (tab.getTitle().equals("Friendly Tab Name")) {
                assert tab.getFieldGroups().size() == 3
                for (FieldGroup fieldGroup : tab.getFieldGroups()) {
                    if (fieldGroup.getKey().equals("TestGroupName")) {
                        assert fieldGroup.getFields().size() == 0
                    } else if (fieldGroup.getKey().equals("OtherTabGroup")) {
                        assert fieldGroup.getFields().size() == 0
                    } else if (fieldGroup.getKey().equals("TestGroupName2")) {
                        assert fieldGroup.getFields().size() == 0
                    } else {
                        assert false
                    }
                }
            } else {
                assert false
            }
        }
    }
}
