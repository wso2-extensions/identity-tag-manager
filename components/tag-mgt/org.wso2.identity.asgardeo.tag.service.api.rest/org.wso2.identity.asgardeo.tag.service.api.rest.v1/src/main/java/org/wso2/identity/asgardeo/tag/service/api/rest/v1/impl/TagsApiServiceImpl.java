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

package org.wso2.identity.asgardeo.tag.service.api.rest.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.asgardeo.tag.service.api.rest.commons.Constants;
import org.wso2.asgardeo.tag.service.api.rest.commons.ContextLoader;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.identity.asgardeo.tag.service.api.rest.v1.*;
import org.wso2.identity.asgardeo.tag.service.api.rest.v1.core.TagManagementAPIService;
import org.wso2.identity.asgardeo.tag.service.api.rest.v1.model.*;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Response;

public class TagsApiServiceImpl implements TagsApiService {

    @Autowired
    private TagManagementAPIService tagManagementApiService;

    @Override
    public Response associateWithApplication(String applicationId,
                                             ApplicationAssociationRequest applicationAssociationRequest) {

        tagManagementApiService.associateWithApplication(applicationId, applicationAssociationRequest);
        return Response.accepted().build();
    }

    @Override
    public Response createTag(TagCreateRequest tagCreateRequest) {

        String tagUuid = "";
        try {
            tagUuid = tagManagementApiService.createTag(tagCreateRequest);
        } catch (UserStoreException e) {
            e.printStackTrace();
        }
        return Response.created(getResourceLocation(tagUuid)).build();
    }

    @Override
    public Response deleteTag(String tagId) {

        tagManagementApiService.deleteTag(tagId);
        return Response.noContent().build();
    }

    @Override
    public Response filterTags(Integer limit, Integer offset, String filter) {

        return Response.ok().entity(tagManagementApiService.filterTags(limit, offset, filter)).build();
    }

    @Override
    public Response getApplicationTags(String applicationId) {

        return Response.ok().entity(tagManagementApiService.getApplicationTags(applicationId)).build();
    }

    @Override
    public Response getTag(String tagId) {

        return Response.ok().entity(tagManagementApiService.getTag(tagId)).build();
    }

    @Override
    public Response getTagsTypes(Integer limit, Integer offset) {

        return Response.ok().entity(tagManagementApiService.getTagTypes(limit, offset)).build();
    }

    @Override
    public Response patchTag(String tagId, TagUpdateRequest tagUpdateRequest) {

        tagManagementApiService.patchTag(tagId, tagUpdateRequest);
        return Response.noContent().build();
    }

    @Override
    public Response patchTagApplicationAssociations(String applicationId,
                                                    PatchApplicationAssociationRequest patchApplicationAssociationRequest) {

        tagManagementApiService.patchTagApplicationAssociations(applicationId, patchApplicationAssociationRequest);
        return Response.ok().build();
    }

    private URI getResourceLocation(String tagUuid) {

        return ContextLoader.buildURIForHeader(Constants.V1_API_PATH_COMPONENT +
                Constants.TAG_MANAGEMENT_PATH_COMPONENT + "/" + tagUuid);
    }
}