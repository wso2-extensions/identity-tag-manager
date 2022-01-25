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

package org.wso2.carbon.identity.tag.manager.model;

/**
 * The Object model for the Tag.
 */
public class Tag {

    private String tagUuid;
    private String name;
    private String type;
    private String description;
    private boolean isPubliclyVisible;
    private String tenantUuid;

    public Tag() {

    }

    public String getTagUuid() {

        return tagUuid;
    }

    public void setTagUuid(String tagUuid) {

        this.tagUuid = tagUuid;
    }

    public String getType() {

        return type;
    }

    public void setAssociationType(String associationType) {

        this.type = associationType;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public boolean isPubliclyVisible() {

        return isPubliclyVisible;
    }

    public void setPubliclyVisible(boolean publiclyVisible) {

        isPubliclyVisible = publiclyVisible;
    }

    public String getTenantUuid() {

        return tenantUuid;
    }

    public void setTenantId(String tenantUuid) {

        this.tenantUuid = tenantUuid;
    }
}
