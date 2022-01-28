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

package org.wso2.carbon.identity.tag.manager.api.rest.v1.core;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.Constants;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.ContextLoader;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.TagManagementDataHolder;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.error.APIError;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.error.ErrorResponse;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.core.model.ExpressionNode;
import org.wso2.carbon.identity.core.model.FilterTreeBuilder;
import org.wso2.carbon.identity.core.model.Node;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.tag.manager.core.constant.TagMgtConstants.ErrorMessage;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.ApplicationAssociationRequest;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.PatchApplicationAssociation;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagCreateRequest;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagListItem;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagListResponse;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagResponse;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagUpdateRequest;
import org.wso2.carbon.identity.tag.manager.exception.TagServiceClientException;
import org.wso2.carbon.identity.tag.manager.exception.TagServiceException;
import org.wso2.carbon.identity.tag.manager.model.Tag;
import org.wso2.carbon.identity.tag.manager.model.TagAssociationsResult;
import org.wso2.carbon.identity.tag.manager.model.TagListResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import static org.wso2.carbon.identity.tag.manager.api.rest.commons.Constants.ERROR_PREFIX;

/**
 * Calls internal osgi services to perform tag management operations.
 */
public class TagManagementAPIService {

    private static final Log LOG = LogFactory.getLog(TagManagementAPIService.class);
    private static final List<String> CONFLICT_ERROR_SCENARIOS = Collections.singletonList(
            ErrorMessage.ERROR_CODE_TAG_ALREADY_EXISTS.getCode());
    private static final List<String> NOT_FOUND_ERROR_SCENARIOS = Collections.singletonList(
            ErrorMessage.ERROR_CODE_TAG_DATA_DOES_NOT_EXIST.getCode());
    private static final List<String> SEARCH_SUPPORTED_FIELDS = new ArrayList<>();

    // Filter related constants.
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String FILTER_STARTS_WITH = "sw";
    private static final String FILTER_ENDS_WITH = "ew";
    private static final String FILTER_EQUALS = "eq";
    private static final String FILTER_CONTAINS = "co";
    private static final int DEFAULT_OFFSET = 0;

    static {

        SEARCH_SUPPORTED_FIELDS.add(TAG_NAME);
        SEARCH_SUPPORTED_FIELDS.add(TAG_TYPE);
    }

    /**
     * Create a tag.
     *
     * @param tagCreateRequest Tag data.
     * @return UUID of the newly created tag.
     */
    public String createTag(TagCreateRequest tagCreateRequest) {

        validateTagCreateRequest(tagCreateRequest);
        String tenantId = getTenantId();
        String tagUuid;
        Tag tag = convertToTag(tagCreateRequest, tenantId);
        try {
            tagUuid = TagManagementDataHolder.getTagManagementService().addTag(tag);
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_STORE_TAG, tagCreateRequest.getName(),
                    tenantId);
        }
        return tagUuid;
    }

    /**
     * Retrieve a tag.
     *
     * @param tagUuid Tag UUID.
     * @return Tag data.
     */
    public TagResponse getTag(String tagUuid) {

        TagResponse tagResponse;
        try {
            validateTagUuid(tagUuid);
            Tag tag = TagManagementDataHolder.getTagManagementService().getTagById(tagUuid);
            tagResponse = convertToTagResponse(tag);
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_RETRIEVE_TAG, tagUuid);
        }
        return tagResponse;
    }

    /**
     * Retrieve a list of filtered tags.
     *
     * @param limit     Limit.
     * @param offset    Offset.
     * @param sortBy    Sort By.
     * @param sortOrder Sort Order.
     * @param filter    Filter string. E.g. filter="name" sw "test" and "type" eq "APPLICATION"
     * @return TagListResponse including .
     */
    public TagListResponse filterTags(int limit, int offset, String sortBy, String sortOrder, String filter) {

        limit = validateAndGetLimit(limit);
        offset = validateAndGetOffset(offset);
        TagListResult tagListResult;
        try {
            tagListResult = TagManagementDataHolder.getTagManagementService()
                    .filterTags(limit, offset, createFormattedFilter(filter), sortBy, sortOrder);
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_RETRIEVE_ALL_TAGS);
        }
        return convertToTagListResponse(tagListResult);
    }

    /**
     * Patch tag.
     *
     * @param tagUuid          Tag UUID.
     * @param tagUpdateRequest TagUpdateRequest
     */
    public void patchTag(String tagUuid, TagUpdateRequest tagUpdateRequest) {

        String tenantId = getTenantId();
        validateTagUuid(tagUuid);
        try {
            TagManagementDataHolder.getTagManagementService().updateTag(tagUuid, tagUpdateRequest.getName(),
                    tagUpdateRequest.getDescription(), tagUpdateRequest.getIsPublic());
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_UPDATE_TAG, tagUuid, tenantId);
        }
    }

    /**
     * Remove tag by tag UUID.
     *
     * @param tagUuid UUID of the tag.
     */
    public void deleteTag(String tagUuid) {

        validateTagUuid(tagUuid);
        try {
            TagManagementDataHolder.getTagManagementService().deleteTag(tagUuid);
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_REMOVE_TAG, tagUuid);
        }
    }

    /**
     * Create a tag-resource association. (Initially supported with APPLICATION as a resource.)
     *
     * @param applicationId                 UUID of the application.
     * @param applicationAssociationRequest List of UUIDs of tags which should be associated with the application.
     */
    public void associateWithApplication(String applicationId,
                                         ApplicationAssociationRequest applicationAssociationRequest) {

        try {
            for (String tagUuid : applicationAssociationRequest.getTagIds()) {
                TagManagementDataHolder.getTagManagementService().addAssociation(tagUuid, applicationId);
            }
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_STORE_ASSOCIATION);
        }
    }

    /**
     * Retrieve tag details for a given application UUID.
     *
     * @return List of tag data for a given application UUID.
     */
    public List<TagListItem> getApplicationTags(String resourceUuid) {

        TagAssociationsResult tagAssociationsResult;
        List<TagListItem> tagListItems = new ArrayList<>();
        try {
            tagAssociationsResult = TagManagementDataHolder.getTagManagementService()
                    .getTagAssociationsByResourceId(resourceUuid);
            for (Tag tag : tagAssociationsResult.getAssociations()) {
                TagListItem tagListItem = tagToListItem(tag);
                tagListItems.add(tagListItem);
            }
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_GET_ASSOCIATION);
        }
        return tagListItems;
    }

    /**
     * Patch tag-resource association.
     *
     * @param applicationId                      UUID of the application.
     * @param patchApplicationAssociationRequest List of objects for the associations consist of
     *                                           operation(add/remove) and tag Ids.
     */
    public void patchTagApplicationAssociations(String applicationId, List<PatchApplicationAssociation>
            patchApplicationAssociationRequest) {

        for (PatchApplicationAssociation patchApplicationAssociation : patchApplicationAssociationRequest) {
            for (String tagUuid : patchApplicationAssociation.getTagIds()) {
                if (patchApplicationAssociation.getOp().value().equals("add")) {
                    try {
                        TagManagementDataHolder.getTagManagementService().addAssociation(tagUuid, applicationId);
                    } catch (TagServiceException e) {
                        throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_STORE_ASSOCIATION);
                    }
                } else if (patchApplicationAssociation.getOp().value().equals("remove")) {
                    try {
                        TagManagementDataHolder.getTagManagementService().deleteAssociation(tagUuid, applicationId);
                    } catch (TagServiceException e) {
                        throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_REMOVE_ASSOCIATION);
                    }
                }
            }
        }
    }

    /**
     * Retrieve tag types.
     *
     * @param limit     Limit.
     * @param offset    Offset.
     * @param sortBy    Sort By.
     * @param sortOrder Sort Order.
     * @return List of tag types.
     */
    public List<String> getTagTypes(int limit, int offset, String sortBy, String sortOrder) {

        limit = validateAndGetLimit(limit);
        offset = validateAndGetOffset(offset);
        List<String> tagList;

        try {
            tagList = TagManagementDataHolder.getTagManagementService().getTagTypes(limit, offset, sortBy, sortOrder);
        } catch (TagServiceException e) {
            throw handleException(e, Constants.ErrorMessages.ERROR_UNABLE_TO_GET_TAG_TYPES);
        }
        return tagList;
    }

    private String createFormattedFilter(String filter) throws TagServiceException {

        Node expressionNode = buildFilterNode(filter);
        String leftFormattedFilter = "";
        String rightFormattedFilter = "";
        String formattedFilter;
        assert expressionNode != null;
        ExpressionNode leftNode = (ExpressionNode) expressionNode.getLeftNode();
        if (leftNode != null) {
            leftFormattedFilter = generateFilterString(leftNode.getOperation(), leftNode.getValue());
        }
        ExpressionNode rightNode = (ExpressionNode) expressionNode.getRightNode();
        if (rightNode != null) {
            rightFormattedFilter = generateFilterString(rightNode.getOperation(), rightNode.getValue());
        }
        ExpressionNode test = (ExpressionNode) expressionNode;
        formattedFilter = leftFormattedFilter + test.getOperation() + rightFormattedFilter;
        return formattedFilter;
    }

    private TagListItem tagToListItem(Tag tag) {

        TagListItem tagListItem = new TagListItem();
        tagListItem.setId(tag.getTagUuid());
        tagListItem.setName(tag.getName());
        tagListItem.setIsPublic(tag.isPubliclyVisible());
        return tagListItem;
    }

    private Tag convertToTag(TagCreateRequest tagCreateRequest, String tenantId) {

        Tag tag = new Tag();
        tag.setName(tagCreateRequest.getName());
        tag.setDescription(tagCreateRequest.getDescription());
        tag.setPubliclyVisible(tagCreateRequest.getIsPublic());
        tag.setTenantId(tenantId);
        tag.setAssociationType(tagCreateRequest.getType().value());
        return tag;
    }

    private TagResponse convertToTagResponse(Tag tag) {

        TagResponse tagResponse = new TagResponse();
        tagResponse.setName(tag.getName());
        tagResponse.setDescription(tag.getDescription());
        tagResponse.setIsPublic(tag.isPubliclyVisible());
        tagResponse.setId(tag.getTagUuid());
        tagResponse.setTypeId(tag.getType());
        return tagResponse;
    }

    private TagListResponse convertToTagListResponse(TagListResult tagList) {

        TagListResponse tagListResponse = new TagListResponse();

        tagListResponse.setCount(tagList.gettotalAvailableResultss());

        List<TagListItem> tagListItemList = new ArrayList<>();
        for (Tag tag : tagList.getTagList()) {
            TagListItem tagListItem = tagToListItem(tag);
            tagListItemList.add(tagListItem);
        }

        tagListResponse.setTags(tagListItemList);
        return tagListResponse;
    }

    private int validateAndGetOffset(Integer offset) {

        if (offset != null && offset >= 0) {
            return offset;
        } else {
            return DEFAULT_OFFSET;
        }
    }

    private int validateAndGetLimit(Integer limit) {

        final int maximumItemPerPage = IdentityUtil.getMaximumItemPerPage();
        if (limit != null && limit > 0 && limit <= maximumItemPerPage) {
            return limit;
        } else {
            return IdentityUtil.getDefaultItemsPerPage();
        }
    }

    private void validateTagCreateRequest(TagCreateRequest tagCreateRequest) {

        if (StringUtils.isBlank(tagCreateRequest.getName())) {
            throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.ERROR_CODE_EMPTY_TAG_NAME);
        }
        if (StringUtils.isBlank(tagCreateRequest.getType().value())) {
            throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.ERROR_CODE_EMPTY_TAG_TYPE);
        }
        if (!validateName(tagCreateRequest.getName())) {
            throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.ERROR_CODE_INVALID_TAG_NAME);
        }
    }

    private boolean validateName(String name) {

        String regexName = "\\p{Upper}(\\p{Lower}+\\s?)";
        String patternName = "(" + regexName + "){2,3}";
        return name.matches(patternName);
    }

    private void validateTagUuid(String tagUuid) {

        if (StringUtils.isBlank(tagUuid)) {
            throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.ERROR_CODE_EMPTY_TAG_UUID);
        }
    }

    private String getTenantId() {

        String tenantDomain = ContextLoader.getTenantDomainFromAuthUser();
        if (StringUtils.isBlank(tenantDomain)) {
            throw handleException(
                    Constants.ErrorMessages.ERROR_COMMON_SERVER_ERROR.getCode(),
                    Constants.ErrorMessages.ERROR_COMMON_SERVER_ERROR.getMessage(),
                    "Unable to retrieve tenant information.");
        }
        int tenantId = IdentityTenantUtil.getTenantId(tenantDomain);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Resolved tenant information. TenantDomain: " + tenantDomain + " tenantId: " + tenantId);
        }
        return Integer.toString(tenantId);
    }

    private APIError handleException(TagServiceException e, Constants.ErrorMessages errorEnum) {

        return handleException(e, errorEnum, StringUtils.EMPTY);
    }

    private APIError handleException(TagServiceException e, Constants.ErrorMessages errorEnum,
                                     String... data) {

        ErrorResponse errorResponse;
        Response.Status status;
        if (e instanceof TagServiceClientException) {
            status = Response.Status.BAD_REQUEST;
            if (isConflictScenario(e.getErrorCode())) {
                status = Response.Status.CONFLICT;
            } else if (isNotFoundScenario(e.getErrorCode())) {
                status = Response.Status.NOT_FOUND;
            }
            if (canPassServerErrorForward(e)) {
                errorResponse = getErrorBuilder(errorEnum.getCode(), errorEnum.getMessage(), e.getMessage())
                        .build(LOG, e, e.getMessage(), true);
            } else {
                errorResponse = getErrorBuilder(errorEnum, data)
                        .build(LOG, e, buildErrorDescription(errorEnum, data), true);
            }
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            errorResponse = getErrorBuilder(errorEnum, data)
                    .build(LOG, e, buildErrorDescription(errorEnum, data), false);
        }
        return new APIError(status, errorResponse);
    }

    private APIError handleException(String errorCode, String errorMessage, String errorDescription) {

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = getErrorBuilder(errorCode, errorMessage, errorDescription)
                .build(LOG, errorDescription, false);
        return new APIError(status, errorResponse);
    }

    private APIError handleError(Response.Status status, Constants.ErrorMessages error, String... data) {

        return new APIError(status, getErrorBuilder(error, data).build());
    }

    private boolean canPassServerErrorForward(TagServiceException e) {

        return StringUtils.contains(e.getErrorCode(),
                ERROR_PREFIX) && StringUtils.isNotEmpty(e.getMessage());
    }

    private boolean isConflictScenario(String errorCode) {

        return !StringUtils.isBlank(errorCode) && CONFLICT_ERROR_SCENARIOS.contains(errorCode);
    }

    private boolean isNotFoundScenario(String errorCode) {

        return !StringUtils.isBlank(errorCode) && NOT_FOUND_ERROR_SCENARIOS.contains(errorCode);
    }

    private ErrorResponse.Builder getErrorBuilder(Constants.ErrorMessages errorEnum, String... data) {

        return new ErrorResponse.Builder().withCode(errorEnum.getCode()).withMessage(errorEnum.getMessage())
                .withDescription(buildErrorDescription(errorEnum, data));
    }

    private ErrorResponse.Builder getErrorBuilder(String errorCode, String errorMessage, String errorDescription) {

        return new ErrorResponse.Builder().withCode(errorCode).withMessage(errorMessage)
                .withDescription(errorDescription);
    }

    private String buildErrorDescription(Constants.ErrorMessages errorEnum, String... data) {

        String description;
        if (ArrayUtils.isNotEmpty(data)) {
            description = String.format(errorEnum.getDescription(), data);
        } else {
            description = errorEnum.getDescription();
        }
        return description;
    }

    private Node buildFilterNode(String filter) {

        if (StringUtils.isBlank(filter)) {
            throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.ERROR_CODE_EMPTY_TAG_UUID);
        }
        try {
            FilterTreeBuilder filterTreeBuilder = new FilterTreeBuilder(filter);
            Node rootNode = filterTreeBuilder.buildTree();
            if (rootNode instanceof ExpressionNode) {
                ExpressionNode leftNode = (ExpressionNode) rootNode.getLeftNode();
                ExpressionNode rightNode = (ExpressionNode) rootNode.getRightNode();
                if (SEARCH_SUPPORTED_FIELDS.contains(leftNode.getAttributeValue()) ||
                        SEARCH_SUPPORTED_FIELDS.contains(rightNode.getAttributeValue())) {
                    return rootNode;
                } else {
                    throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.INVALID_FILTER_ATTRIBUTE);
                }
            } else {
                throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.INVALID_FILTER_FORMAT);
            }
        } catch (IOException | IdentityException e) {
            throw handleError(Response.Status.BAD_REQUEST, Constants.ErrorMessages.INVALID_FILTER_FORMAT);
        }
    }

    private String generateFilterString(String searchOperation, String searchValue)
            throws TagServiceException {

        String formattedFilter;
        switch (searchOperation) {
            case FILTER_STARTS_WITH:
                formattedFilter = searchValue + "*";
                break;
            case FILTER_ENDS_WITH:
                formattedFilter = "*" + searchValue;
                break;
            case FILTER_EQUALS:
                formattedFilter = searchValue;
                break;
            case FILTER_CONTAINS:
                formattedFilter = "*" + searchValue + "*";
                break;
            default:
                throw new TagServiceException(Constants.ErrorMessages.INVALID_FILTER_OPERATION.getCode(),
                        Constants.ErrorMessages.INVALID_FILTER_OPERATION.toString());
        }
        return formattedFilter;
    }
}
