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

package org.wso2.carbon.identity.tag.manager.core.dao;

import org.wso2.carbon.identity.tag.manager.exception.TagServiceException;
import org.wso2.carbon.identity.tag.manager.model.Tag;
import org.wso2.carbon.identity.tag.manager.model.TagAssociationsResult;
import org.wso2.carbon.identity.tag.manager.model.TagListResult;

import java.util.List;

/**
 * DAO interface for the tag  management service.
 */
public interface TagManagementDAO {

    /**
     * Retrieve tag data with the given tag UUID in the given tenant.
     *
     * @param tagUuid    UUID of the tag.
     * @param tenantUuid UUID of the tenant.
     * @return Tag.
     * @throws TagServiceException If an error occurred while getting tag data.
     */
    Tag loadTag(String tagUuid, String tenantUuid) throws TagServiceException;

    /**
     * Filter a list of tags by type and name.
     *
     * @param limit      Number of results to be retrieved.
     * @param offset     Offset.
     * @param sortBy     Sort by.
     * @param sortOrder  Sort order.
     * @param filter     Tag type and name filter.
     * @param tenantUuid UUID of the tenant.
     * @return TagListResponse.
     * @throws TagServiceException If an error occurred while getting the tags list.
     */
    TagListResult filterTags(int limit, int offset, String sortBy, String sortOrder, String filter, String tenantUuid)
            throws TagServiceException;

    /**
     * Retrieve a list of tag types.
     *
     * @param limit     Number of results to be retrieved.
     * @param offset    Offset.
     * @param sortBy    Sort by.
     * @param sortOrder Sort order.
     * @return list of Strings.
     * @throws TagServiceException If an error occurred while getting the tag types.
     */
    List<String> loadTagTypes(int limit, int offset, String sortBy, String sortOrder) throws TagServiceException;

    /**
     * Store tag.
     *
     * @param tag        Tag data.
     * @param tenantUuid UUID of the tenant.
     * @return tagUuid.
     * @throws TagServiceException If an error occurred while adding a tag.
     */
    String storeTag(Tag tag, String tenantUuid) throws TagServiceException;

    /**
     * Delete tag.
     *
     * @param tagUuid    UUID of tag.
     * @param tenantUuid UUID of the tenant.
     * @throws TagServiceException If an error occurred while deleting the tag.
     */
    void deleteTag(String tagUuid, String tenantUuid) throws TagServiceException;

    /**
     * Update tag.
     *
     * @param tagUuid           UUID of tag.
     * @param name              Name of the tag {@link Tag}.
     * @param description       Description of the tag {@link Tag}.
     * @param isPubliclyVisible Public visibility of the tag {@link Tag}.
     * @param tenantUuid        UUID of the tenant.
     * @throws TagServiceException If an error occurred while deleting the tag.
     */
    void updateTag(String tagUuid, String name, String description, boolean isPubliclyVisible, String tenantUuid)
            throws TagServiceException;

    /**
     * Store tag-resource association.
     *
     * @param tagUuid      UUID of the tag.
     * @param resourceUuid UUID of the resource.
     * @param tenantUuid   UUID of the tenant.
     * @throws TagServiceException If an error occurred while adding a tag.
     */
    void storeAssociation(String tagUuid, String resourceUuid, String tenantUuid) throws TagServiceException;

    /**
     * Delete tag-resource association.
     *
     * @param tagUuid      UUID of tag.
     * @param resourceUuid UUID of resource.
     * @throws TagServiceException If an error occurred while deleting the tag-resource association.
     */
    void deleteAssociation(String tagUuid, String resourceUuid) throws TagServiceException;

    /**
     * Get list of tag associations with the given UUID of the resource.
     *
     * @param resourceUuid UUID of resource.
     * @return TagAssociationsResult.
     * @throws TagServiceException If an error occurred while getting the associations.
     */
    TagAssociationsResult loadTagAssociationsByResourceId(String resourceUuid) throws TagServiceException;

    /**
     * Delete all tag associations for the given resource.
     *
     * @param resourceUuid UUID of resource.
     * @throws TagServiceException If an error occurred while deleting the tag association.
     */
    void deleteAssociationByResource(String resourceUuid) throws TagServiceException;

    /**
     * Determine whether a tag is existing with the given tag UUID.
     *
     * @param tagUuid UUID of the Tag.
     * @return Whether a tag is existing with the given tag UUID.
     * @throws TagServiceException If an error occurred while checking the tag existence.
     */
    boolean isExistingTagId(String tagUuid) throws TagServiceException;

    /**
     * Determine whether a tag is existing with the given tag name and type.
     *
     * @param name       Name of the Tag.
     * @param type       Type of the Tag.
     * @param tenantUuid UUID of the tenant.
     * @return Whether a tag is existing with the given tag name and type.
     * @throws TagServiceException If an error occurred while checking the tag existence.
     */
    boolean isExistingTag(String name, String type, String tenantUuid) throws TagServiceException;

    /**
     * Determine whether a tag association is existing with the given tag and resource UUID.
     *
     * @param tagUuid    UUID of the Tag.
     * @param resourceId UUID of the resource.
     * @return Whether an association is existing between the given tag and resource.
     * @throws TagServiceException If an error occurred while checking the tag association existence.
     */
    boolean isExistingAssociation(String tagUuid, String resourceId) throws TagServiceException;

    /**
     * Delete tags by tenant UUID.
     *
     * @param tenantUuid UUID of the tenant.
     * @throws TagServiceException If an error occurred while deleting the tag.
     */
    void deleteTagsByTenant(String tenantUuid) throws TagServiceException;
}
