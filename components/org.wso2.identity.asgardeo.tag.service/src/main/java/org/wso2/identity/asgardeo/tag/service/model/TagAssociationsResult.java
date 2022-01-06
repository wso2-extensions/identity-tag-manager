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

package org.wso2.identity.asgardeo.tag.service.model;

import java.util.List;

/**
 * The object model for the list tag associations.
 */
public class TagAssociationsResult {

    private int totalAvailableResult;
    private List<Tag> associations;

    public TagAssociationsResult(int totalAvailableResult, List<Tag> associations) {

        this.totalAvailableResult = totalAvailableResult;
        this.associations = associations;
    }

    public int getTotalAvailableResult() {

        return totalAvailableResult;
    }

    public List<Tag> getAssociations() {

        return associations;
    }

}

