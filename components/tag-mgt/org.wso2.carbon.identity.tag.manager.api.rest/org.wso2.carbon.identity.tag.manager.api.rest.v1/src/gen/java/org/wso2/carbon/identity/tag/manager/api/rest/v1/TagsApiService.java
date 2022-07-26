/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com).
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

package org.wso2.carbon.identity.tag.manager.api.rest.v1;

import java.util.List;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.ApplicationAssociationRequest;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.PatchApplicationAssociation;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagCreateRequest;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagUpdateRequest;
import javax.ws.rs.core.Response;


public interface TagsApiService {

      public Response associateWithApplication(String applicationId, ApplicationAssociationRequest applicationAssociationRequest);

      public Response createTag(TagCreateRequest tagCreateRequest);

      public Response deleteTag(String tagId);

      public Response filterTags(Integer limit, Integer offset, String filter);

      public Response getApplicationTags(String applicationId);

      public Response getTag(String tagId);

      public Response getTagsTypes(Integer limit, Integer offset);

      public Response patchTag(String tagId, TagUpdateRequest tagUpdateRequest);

      public Response patchTagApplicationAssociations(String applicationId, List<PatchApplicationAssociation> patchApplicationAssociation);
}
