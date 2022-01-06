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

package org.wso2.identity.asgardeo.tag.service.dao;

import org.wso2.identity.asgardeo.tag.service.exception.TagServiceException;
import org.wso2.identity.asgardeo.tag.service.model.Tag;
import org.wso2.identity.asgardeo.tag.service.model.TagAssociationsResult;
import org.wso2.identity.asgardeo.tag.service.model.TagListResponse;

import java.sql.SQLException;

public class TagManagementDAOImpl implements TagManagementDAO{

    @Override
    public Tag loadTag(String tagUuid, String tenantUuid) throws TagServiceException {

        return null;
    }

    @Override
    public Tag loadTag(String name, String type, String tenantUuid) throws TagServiceException {

        return null;
    }

    @Override
    public TagListResponse filterTags(int limit, int offset, String filter, String tenantUuid)
            throws TagServiceException {

        return null;
    }

    @Override
    public String storeTag(Tag tag, String tenantUuid) throws TagServiceException {

        return null;
    }


    @Override
    public void deleteTag(String tagUuid, String tenantUuid) throws TagServiceException {

    }

    @Override
    public void updateTag(String tagUuid, String name, String description, boolean isPubliclyVisible, String tenantUuid)
            throws TagServiceException {

    }

    @Override
    public void storeAssociation(String tagUuid, String resourceUuid, String tenantUuid) throws TagServiceException {

    }

    @Override
    public void deleteAssociation(String tagUuid, String resourceUuid) throws TagServiceException {

    }

    @Override
    public TagAssociationsResult loadTagAssociationsByResourceId(String resourceUuid)
            throws TagServiceException, SQLException {

        return null;
    }

    @Override
    public void deleteAssociationByResource(String resourceUuid) throws TagServiceException {

    }
}
