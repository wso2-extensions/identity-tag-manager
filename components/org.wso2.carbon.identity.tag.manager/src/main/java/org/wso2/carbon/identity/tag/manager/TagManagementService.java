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

package org.wso2.carbon.identity.tag.manager;

import org.wso2.carbon.identity.tag.manager.exception.TagServiceException;
import org.wso2.carbon.identity.tag.manager.model.Tag;
import org.wso2.carbon.identity.tag.manager.model.TagAssociationsResult;
import org.wso2.carbon.identity.tag.manager.model.TagListResult;

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
    Tag getTagById(String tagUuid) throws TagServiceException;

    /**
     * Get a list of tag types.
     *
     * @param limit     Number of results to be retrieved.
     * @param offset    Offset.
     * @param sortBy    Sort By.
     * @param sortOrder Sort Order.
     * @return List<String> List of tag types.
     * @throws TagServiceException If an error occurred while getting the tag types.
     */
    List<String> getTagTypes(int limit, int offset, String sortBy, String sortOrder) throws TagServiceException;

    /**
     * Get a list of tags with the given type.
     *
     * @param limit     Number of results to be retrieved.
     * @param offset    Offset.
     * @param filter    Tag type and name of the tag.
     * @param sortBy    Sort By.
     * @param sortOrder Sort Order.
     * @return List<Tag>.
     * @throws TagServiceException If an error occurred while getting the tags.
     */
    TagListResult filterTags(int limit, int offset, String filter, String sortBy, String sortOrder)
            throws TagServiceException;

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
     * Get list of tag associations with the given UUID of the resource.
     *
     * @param resourceUuid UUID of resource.
     * @return TagAssociationsResult.
     * @throws TagServiceException If an error occurred while getting the associations.
     */
    TagAssociationsResult getTagAssociationsByResourceId(String resourceUuid)
            throws TagServiceException;

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
    void deleteAssociationByResourceId(String resourceUuid) throws TagServiceException;

    /**
     * Delete all tags for a given tenant.
     *
     * @param tenantUuid UUID of tenant.
     * @throws TagServiceException If an error occurred while deleting the tags.
     */
    void deleteTagsByTenantId(String tenantUuid) throws TagServiceException;
}
