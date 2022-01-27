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

package org.wso2.carbon.identity.tag.manager.core.internal.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.tag.manager.TagManagementService;
import org.wso2.carbon.identity.tag.manager.core.dao.DAOFactory;
import org.wso2.carbon.identity.tag.manager.core.dao.TagManagementDAO;
import org.wso2.carbon.identity.tag.manager.core.internal.TagManagementServiceDataHolder;
import org.wso2.carbon.identity.tag.manager.exception.TagServiceClientException;
import org.wso2.carbon.identity.tag.manager.exception.TagServiceException;
import org.wso2.carbon.identity.tag.manager.core.constant.TagMgtConstants.ErrorMessage;
import org.wso2.carbon.identity.tag.manager.model.Tag;
import org.wso2.carbon.identity.tag.manager.model.TagAssociationsResult;
import org.wso2.carbon.identity.tag.manager.model.TagListResult;
import org.wso2.carbon.user.api.Tenant;
import org.wso2.carbon.user.api.UserStoreException;

import java.util.List;

/**
 * Tag management service implementation class.
 */
public class TagManagementServiceImpl implements TagManagementService {

    private static final Log LOG = LogFactory.getLog(TagManagementService.class);
    private String tenantUuid = getTenantUuid();

    public TagManagementServiceImpl() throws TagServiceException {

    }

    @Override
    public String addTag(Tag tag) throws TagServiceException {

        // Check if the tag name is valid.
        if (!isTagNameValid(tag.getName())) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_NAME.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_NAME.getMessage());
        }
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        // Check if tag already existing.
        if (tagManagementDAO.isExistingTag(tag.getName(), tag.getType(), tenantUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_TAG_ALREADY_EXISTS.getCode(),
                    String.format(ErrorMessage.ERROR_CODE_TAG_ALREADY_EXISTS.getMessage(), tag.getName(),
                            tenantUuid));
        }
        String tagUuid = tagManagementDAO.storeTag(tag, tenantUuid);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Retrieved tag UUID: " + tagUuid);
        }
        return tagUuid;
    }

    @Override
    public Tag getTagById(String tagUuid) throws TagServiceException {

        Tag tag;
        if (StringUtils.isBlank(tagUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getMessage());
        }
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        tag = tagManagementDAO.loadTag(tagUuid, tenantUuid);
        if (tag == null) {
            throw new TagServiceClientException(
                    ErrorMessage.ERROR_CODE_TAG_DATA_DOES_NOT_EXIST.getCode(),
                    String.format(ErrorMessage.ERROR_CODE_TAG_DATA_DOES_NOT_EXIST.getMessage(), tagUuid));
        }
        return tag;
    }

    @Override
    public List<String> getTagTypes(int limit, int offset, String sortBy, String sortOrder) throws TagServiceException {

        if (limit < 0) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_INVALID_LIMIT_VALUE.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_LIMIT_VALUE.getMessage());
        }
        if (offset < 0) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_INVALID_OFFSET_VALUE.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_OFFSET_VALUE.getMessage());
        }
        List<String> tagTypes;
        handleSorting(sortBy, sortOrder);
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        tagTypes = tagManagementDAO.loadTagTypes(limit, offset, sortBy, sortOrder);
        if (tagTypes == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Tags have not been defined yet.");
            }
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_EMPTY_TAG_TYPES_LIST.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_LIMIT_VALUE.getMessage());
        }
        return tagTypes;
    }

    @Override
    public TagListResult filterTags(int limit, int offset, String sortBy, String sortOrder, String filter)
            throws TagServiceException {

        TagListResult tagListResult;
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        if (limit < 0) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_INVALID_LIMIT_VALUE.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_LIMIT_VALUE.getMessage());
        }
        if (offset < 0) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_INVALID_OFFSET_VALUE.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_OFFSET_VALUE.getMessage());
        }
        handleSorting(sortBy, sortOrder);

        if (StringUtils.isBlank(filter)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_FILTER_VALUE.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_FILTER_VALUE.getMessage());
        }
        tagListResult = tagManagementDAO.filterTags(limit, offset, null, null, filter, tenantUuid);
        return tagListResult;
    }

    @Override
    public void updateTag(String tagUuid, String name, String description, boolean isPubliclyVisible)
            throws TagServiceException {

        if (StringUtils.isBlank(tagUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getMessage());
        }
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        if (!tagManagementDAO.isExistingTagId(tagUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_INVALID_TAG_UUID.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_TAG_UUID.getMessage());
        }
        if (StringUtils.isBlank(name)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_NAME.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_NAME.getMessage());
        }
        tagManagementDAO.updateTag(tagUuid, name, description, isPubliclyVisible, tenantUuid);
    }

    @Override
    public void deleteTag(String tagUuid) throws TagServiceException {

        if (!isTagUUidValid(tagUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getMessage());
        }
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        if (!tagManagementDAO.isExistingTagId(tagUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_INVALID_TAG_UUID.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_TAG_UUID.getMessage());
        }
        tagManagementDAO.deleteTag(tagUuid, tenantUuid);
    }

    @Override
    public void addAssociation(String tagUuid, String resourceUuid) throws TagServiceException {

        if (StringUtils.isBlank(tagUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getMessage());
        }
        if (StringUtils.isBlank(resourceUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_RESOURCE_UUID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_RESOURCE_UUID.getMessage());
        }
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        tagManagementDAO.storeAssociation(tagUuid, resourceUuid, tenantUuid);
    }

    @Override
    public TagAssociationsResult getTagAssociationsByResourceId(String resourceUuid) throws TagServiceException {

        if (StringUtils.isBlank(resourceUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_RESOURCE_UUID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_RESOURCE_UUID.getMessage());
        }
        TagAssociationsResult tagAssociationsResult;
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        tagAssociationsResult = tagManagementDAO.loadTagAssociationsByResourceId(resourceUuid);
        return tagAssociationsResult;
    }

    @Override
    public void deleteAssociation(String tagUuid, String resourceUuid) throws TagServiceException {

        if (StringUtils.isBlank(tagUuid) && StringUtils.isBlank(resourceUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getMessage());
        }
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        if (!tagManagementDAO.isExistingAssociation(tagUuid, resourceUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_INVALID_ASSOCIATION.getCode(),
                    ErrorMessage.ERROR_CODE_INVALID_ASSOCIATION.getMessage());
        }
        tagManagementDAO.deleteAssociation(tagUuid, resourceUuid);
    }

    @Override
    public void deleteAssociationByResourceId(String resourceUuid) throws TagServiceException {

        if (StringUtils.isBlank(resourceUuid)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_EMPTY_RESOURCE_UUID.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_RESOURCE_UUID.getMessage());
        }
        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        tagManagementDAO.deleteAssociationByResource(resourceUuid);
    }

    @Override
    public void deleteTagsByTenantId(String tenantUuid) throws TagServiceException {

        TagManagementDAO tagManagementDAO = DAOFactory.getInstance().getTagManagementDAO();
        tagManagementDAO.deleteTagsByTenant(tenantUuid);
    }

    private String getTenantUuid() throws TagServiceException {

        try {
            int tenantId = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
            Tenant tenant = TagManagementServiceDataHolder.getRealmService().getTenantManager().getTenant(tenantId);
            if (tenant == null) {
                throw new TagServiceException(ErrorMessage.ERROR_INVALID_TENANT_ID.getCode(),
                        String.format(ErrorMessage.ERROR_INVALID_TENANT_ID.getMessage(), tenantId));
            }
            String tenantUuid = tenant.getTenantUniqueID();
            if (StringUtils.isBlank(tenantUuid)) {
                throw new TagServiceException(ErrorMessage.ERROR_CODE_NO_UNIQUE_ID_FOR_TENANT.getCode(),
                        String.format(ErrorMessage.ERROR_CODE_NO_UNIQUE_ID_FOR_TENANT.getMessage(), tenantId));
            }
            return tenantUuid;
        } catch (UserStoreException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_TENANT_UUID.getCode(),
                    ErrorMessage.ERROR_CODE_ERROR_GETTING_TENANT_UUID.getMessage(), e);
        }
    }

    private void handleSorting(String sortBy, String sortOrder) throws TagServiceClientException {

        if (StringUtils.isNotBlank(sortBy)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_SORT_BY_NOT_IMPLEMENTED.getCode(),
                    ErrorMessage.ERROR_CODE_SORT_BY_NOT_IMPLEMENTED.getDescription());
        }
        if (StringUtils.isNotBlank(sortOrder)) {
            throw new TagServiceClientException(ErrorMessage.ERROR_CODE_SORT_ORDER_NOT_IMPLEMENTED.getCode(),
                    ErrorMessage.ERROR_CODE_SORT_ORDER_NOT_IMPLEMENTED.getDescription());
        }
    }

    private boolean isTagNameValid(String name) {

        if (StringUtils.isBlank(name)) {
            return false;
        }
        String regexName = "\\p{Upper}(\\p{Lower}+\\s?)";
        String patternName = "(" + regexName + "){2,3}";
        return name.matches(patternName);
    }

    private boolean isTagUUidValid(String tagUuid) {

        return StringUtils.isNotBlank(tagUuid);
    }
}
