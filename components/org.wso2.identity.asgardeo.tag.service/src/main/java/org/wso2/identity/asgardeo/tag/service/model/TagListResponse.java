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
 * * The Object model for list of tags.
 */
public class TagListResponse {

    private int totalAvailableResult;
    private List<Tag> tags;

    public TagListResponse(){}

    public TagListResponse(int totalResult, List<Tag> tags) {

        this.setTotalAvailableResult(totalResult);
        this.setTagList(tags);
    }

    public void setTagList(List<Tag> tagList) {

        this.tags = tagList;
    }

    public List<Tag> getTagList() {

        return tags;
    }

    public int getTotalAvailableResult() {

        return totalAvailableResult;
    }

    public void setTotalAvailableResult(int totalAvailableResult) {

        this.totalAvailableResult = totalAvailableResult;
    }
}
