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

package org.wso2.identity.asgardeo.tag.service;

import org.wso2.identity.asgardeo.tag.service.exception.TagServiceException;
import org.wso2.identity.asgardeo.tag.service.model.Tag;
import org.wso2.identity.asgardeo.tag.service.model.TagAssociationsResult;
import org.wso2.identity.asgardeo.tag.service.model.TagListResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * This is the service interface for managing tags.
 */
public interface TagManagementService {

    /**
     * Add tag.
     *
     * @param tag Tag details.
     * @return Tag UUID.
     * @throws TagServiceException If an error occurred while adding a tag.
     */
    String addTag(Tag tag) throws TagServiceException;

    /**
     * Get tag attributes with the given UUID.
     *
     * @param tagUuid UUID of the tag.
     * @return Tag.
     * @throws TagServiceException If an error occurred while getting the tag attributes.
     */
    Tag getTag(String tagUuid) throws TagServiceException;

    /**
     * Get a list of tag types.
     *
     * @param limit  Number of results to be retrieved.
     * @param offset Offset.
     * @return List<Tag>.
     * @throws TagServiceException If an error occurred while getting the tag types.
     */
    List<Tag> getTagTypes(int limit, int offset) throws TagServiceException;

    /**
     * Get a list of tags with the given type.
     *
     * @param limit  Number of results to be retrieved.
     * @param offset Offset.
     * @param filter Tag type and name of the tag.
     * @return List<Tag>.
     * @throws TagServiceException If an error occurred while getting the tags.
     */
    TagListResponse filterTags(int limit, int offset, String filter) throws TagServiceException;

    /**
     * Update tag.
     *
     * @param tagUuid           UUID of tag.
     * @param name              Name of the tag {@link Tag}.
     * @param description       Description of the tag {@link Tag}.
     * @param isPubliclyVisible Public visibility of the tag {@link Tag}.
     * @throws TagServiceException If an error occurred while updating the tag.
     */
    void updateTag(String tagUuid, String name, String description, boolean isPubliclyVisible)
            throws TagServiceException;

    /**
     * Delete tag.
     *
     * @param tagUuid UUID of tag.
     * @throws TagServiceException If an error occurred while deleting the tag.
     */
    void deleteTag(String tagUuid) throws TagServiceException;

    /**
     * Add tag-resource association.
     *
     * @param tagUuid      UUID of the tag.
     * @param resourceUuid UUID of the resource.
     * @throws TagServiceException If an error occurred while adding a tag.
     */
    void addAssociation(String tagUuid, String resourceUuid) throws TagServiceException;

    /**
     * Get list of tag associations with the given UUID of the application.
     *
     * @param applicationUuid UUID of application.
     * @return TagAssociationsResult.
     * @throws TagServiceException If an error occurred while getting the associations.
     */
    TagAssociationsResult getTagAssociationsByApplicationId(String applicationUuid)
            throws TagServiceException, SQLException;

    /**
     * Delete tag-resource association.
     *
     * @param tagUuid      UUID of tag.
     * @param resourceUuid UUID of resource.
     * @throws TagServiceException If an error occurred while deleting the tag-resource association.
     */
    void deleteAssociation(String tagUuid, String resourceUuid) throws TagServiceException;

    /**
     * Delete all tag associations for a given resource.
     *
     * @param resourceUuid UUID of resource.
     * @throws TagServiceException If an error occurred while deleting the tag association.
     */
    void deleteAssociationByResource(String resourceUuid) throws TagServiceException;

    /**
     * Delete all tags for a given tenant.
     *
     * @param tenantUuid UUID of tenant.
     * @throws TagServiceException If an error occurred while deleting the tags.
     */
    void deleteTagsByTenant(String tenantUuid) throws TagServiceException;

}

