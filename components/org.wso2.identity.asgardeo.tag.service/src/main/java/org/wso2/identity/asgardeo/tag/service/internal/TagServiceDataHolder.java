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

package org.wso2.identity.asgardeo.tag.service.internal;

import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.identity.asgardeo.tag.service.TagManagementService;

/**
 * This class acts as a data holder to the tag service manager.
 */
public class TagServiceDataHolder {

    private static RealmService realmService;
    public static TagManagementService tagManagementService;

    /**
     * Get the RealmService.
     *
     * @return RealmService.
     */
    public static RealmService getRealmService() {

        if (realmService == null) {
            throw new RuntimeException("RealmService was not set during the " +
                    "TagManagerServiceComponent startup");
        }
        return realmService;
    }

    /**
     * Set the RealmService.
     *
     * @param realmService RealmService.
     */
    public static void setRealmService(RealmService realmService) {

        TagServiceDataHolder.realmService = realmService;
    }

    /**
     * Get TagManagementService.
     *
     * @return TagManagementService.
     */
    public static TagManagementService getTagManagementService() {

        if (tagManagementService == null) {
            throw new RuntimeException("TagManagementService was not set during the " +
                    "TagManagementService startup");
        }
        return tagManagementService;
    }

    /**
     * Set TagManagementService.
     *
     * @param tagManagementService TagManagementService.
     */
    public static void setTagManagementService(TagManagementService tagManagementService) {

        TagServiceDataHolder.tagManagementService = tagManagementService;
    }

}


