/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.com).
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.tag.manager.core.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.tag.manager.TagManagementService;
import org.wso2.carbon.identity.tag.manager.core.internal.impl.TagManagementServiceImpl;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * Service component class of the tag service manager.
 */
@Component(
        name = "org.wso2.identity.tag.manager.service.component",
        immediate = true
)
public class TagManagementServiceComponent {

    private static final Log
            LOG = LogFactory.getLog(TagManagementServiceComponent.class);

    @Activate
    protected void activate(ComponentContext ctxt) {

        try {
            BundleContext bundleContext = ctxt.getBundleContext();
            bundleContext.registerService(TagManagementService.class, new TagManagementServiceImpl(), null);
            LOG.info("TagManagementService service component activated successfully.");
        } catch (Exception e) {
            LOG.error("Failed to activate TagManagementService service component.", e);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

        if (LOG.isDebugEnabled()) {
            LOG.info("TagManagementService bundle is deactivated");
        }
    }

    @Reference(
            name = "RealmService",
            service = org.wso2.carbon.user.core.service.RealmService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRealmService")
    protected void setRealmService(RealmService realmService) {

        TagManagementServiceDataHolder.setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {

        TagManagementServiceDataHolder.setRealmService(null);
    }
}
