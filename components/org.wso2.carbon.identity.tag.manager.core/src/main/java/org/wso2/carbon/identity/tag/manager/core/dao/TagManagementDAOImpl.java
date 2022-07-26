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

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.tag.manager.core.constant.TagMgtConstants;
import org.wso2.carbon.identity.tag.manager.exception.TagServiceException;
import org.wso2.carbon.identity.tag.manager.core.constant.TagMgtConstants.ErrorMessage;
import org.wso2.carbon.identity.tag.manager.model.Tag;
import org.wso2.carbon.identity.tag.manager.model.TagAssociationsResult;
import org.wso2.carbon.identity.tag.manager.model.TagListResult;
import org.wso2.carbon.registry.core.utils.UUIDGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tag management service DAO implementation class.
 */
public class TagManagementDAOImpl implements TagManagementDAO {

    @Override
    public Tag loadTag(String tagUuid, String tenantUuid) throws TagServiceException {

        ResultSet resultSet;
        Tag tag = null;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false);
             PreparedStatement prepStmt =
                     connection.prepareStatement(TagMgtConstants.SQLConstants.GET_IDN_TAG_BY_TAG_UUID_SQL)) {
            prepStmt.setString(1, tagUuid);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                tag = new Tag();
                tag.setTagUuid(resultSet.getString(1));
                tag.setName(resultSet.getString(2));
                tag.setDescription(resultSet.getString(3));
                tag.setPubliclyVisible(isPublic(resultSet.getString(4)));
                tag.setAssociationType(resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_LOADING_TAG.getCode(), e.getMessage());
        }
        return tag;
    }

    @Override
    public TagListResult filterTags(int limit, int offset, String sortBy, String sortOrder, String filter,
                                    String tenantUuid) throws TagServiceException {

        TagListResult tagListResult = new TagListResult();
        if (limit == 0) {
            return tagListResult;
        }
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.LOAD_IDN_TAGS_BY_TYPE_NAME_TENANT_UUID)) {
            ResultSet resultSet;
            List<Tag> tagList = new ArrayList<>();
            String filterResolvedForSQL = resolveSQLFilter(filter);
            prepStmt.setString(1, tenantUuid);
            prepStmt.setString(2, filterResolvedForSQL);
            prepStmt.setInt(3, limit);
            prepStmt.setInt(4, offset);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setTagUuid(resultSet.getString(1));
                tag.setName(resultSet.getString(2));
                tag.setDescription(resultSet.getString(3));
                tag.setPubliclyVisible(isPublic(resultSet.getString(4)));
                tagList.add(tag);
            }
            tagListResult.setTagList(tagList);
            tagListResult.settotalAvailableResultss(tagList.size());
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_TAGS_FROM_TYPE_TENANT_UUID.getCode(),
                    e.getMessage());
        }
        return tagListResult;
    }

    @Override
    public List<String> loadTagTypes(int limit, int offset, String sortBy, String sortOrder)
            throws TagServiceException {

        ResultSet resultSet;
        List<String> tagTypesList = new ArrayList<>();
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false);
             PreparedStatement prepStmt =
                     connection.prepareStatement(TagMgtConstants.SQLConstants.LOAD_IDN_TAG_TYPES)) {
            prepStmt.setInt(1, limit);
            prepStmt.setInt(2, offset);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString(1);
                if (StringUtils.isNotBlank(type)) {
                    tagTypesList.add(type);
                }
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_LOADING_TAG_TYPES.getCode(), e.getMessage());
        }
        return tagTypesList;
    }

    @Override
    public String storeTag(Tag tag, String tenantUuid) throws TagServiceException {

        String tagUuid = UUIDGenerator.generateUUID();
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true);
             PreparedStatement prepStmt = connection.prepareStatement(TagMgtConstants.SQLConstants.ADD_IDN_TAG_SQL)) {
            prepStmt.setString(1, tagUuid);
            prepStmt.setString(2, tag.getName());
            prepStmt.setString(3, tag.getDescription());
            prepStmt.setString(4, (tag.isPubliclyVisible()? "1":"0"));
            prepStmt.setString(5, tag.getType());
            prepStmt.setString(6, tenantUuid);
            prepStmt.execute();
            IdentityDatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_ADDING_TAG.getCode(),
                    ErrorMessage.ERROR_CODE_ERROR_ADDING_TAG.getMessage(), e);
        }
        return tagUuid;
    }

    @Override
    public void deleteTag(String tagUuid, String tenantUuid) throws TagServiceException {

        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.DELETE_IDN_TAG_SQL)) {
            prepStmt.setString(1, tagUuid);
            prepStmt.setString(2, tenantUuid);
            prepStmt.execute();
            IdentityDatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_DELETING_TAG.getCode(),
                    ErrorMessage.ERROR_CODE_EMPTY_TAG_ID.getMessage(), e);
        }
    }

    @Override
    public void updateTag(String tagUuid, String name, String description, boolean isPubliclyVisible, String tenantUuid)
            throws TagServiceException {

        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.UPDATE_IDN_TAG_SQL)) {
            prepStmt.setString(1, name);
            prepStmt.setString(2, description);
            prepStmt.setBoolean(3, isPubliclyVisible);
            prepStmt.setString(4, tagUuid);
            prepStmt.setString(5, tenantUuid);
            prepStmt.execute();
            IdentityDatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_UPDATING_TAG.getCode(),
                    ErrorMessage.ERROR_CODE_ERROR_UPDATING_TAG.getMessage(), e);
        }
    }

    @Override
    public void storeAssociation(String tagUuid, String resourceUuid, String tenantUuid) throws TagServiceException {

        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.ADD_TAG_RESOURCE_ASC_SQL)) {
            prepStmt.setString(1, tagUuid);
            prepStmt.setString(2, resourceUuid);
            prepStmt.setString(3, tenantUuid);
            prepStmt.execute();
            IdentityDatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_ADDING_ASSOCIATION.getCode(),
                    ErrorMessage.ERROR_CODE_ERROR_ADDING_ASSOCIATION.getMessage(), e);
        }
    }

    @Override
    public void deleteAssociation(String tagUuid, String resourceUuid) throws TagServiceException {

        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.DELETE_TAG_RESOURCE_ASC_SQL)) {
            prepStmt.setString(1, tagUuid);
            prepStmt.setString(2, resourceUuid);
            prepStmt.execute();
            IdentityDatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_DELETING_ASSOCIATION.getCode(),
                    ErrorMessage.ERROR_CODE_ERROR_DELETING_ASSOCIATION.getMessage(), e);
        }
    }

    @Override
    public TagAssociationsResult loadTagAssociationsByResourceId(String resourceUuid)
            throws TagServiceException {

        List<Tag> tagList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.GET_TAG_RESOURCE_ASC_BY_RESOURCE_UUID_SQL)) {
            prepStmt.setString(1, resourceUuid);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setTagUuid(resultSet.getString(1));
                tag.setName(resultSet.getString(2));
                tag.setPubliclyVisible(isPublic(resultSet.getString(3)));
                tagList.add(tag);
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_ASSOCIATIONS.getCode(), e.getMessage());
        }
        return new TagAssociationsResult(tagList.size(), tagList);
    }

    @Override
    public void deleteAssociationByResource(String resourceUuid) throws TagServiceException {

        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.DELETE_TAG_RESOURCE_ASC_BY_RESOURCE_SQL)) {
            prepStmt.setString(1, resourceUuid);
            prepStmt.execute();
            IdentityDatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_DELETING_ASSOCIATION.getCode(),
                    ErrorMessage.ERROR_CODE_ERROR_DELETING_ASSOCIATION.getMessage(), e);
        }
    }

    @Override
    public boolean isExistingAssociation(String tagUuid, String resourceId) throws TagServiceException {

        ResultSet resultSet;
        boolean status = false;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.IS_TAG_ASC_EXISTING_SQL)) {
            prepStmt.setString(1, tagUuid);
            prepStmt.setString(2, resourceId);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getBoolean(1)) {
                    status = true;
                }
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_ASSOCIATION.getCode(), e.getMessage());
        }
        return status;
    }

    @Override
    public boolean isExistingTagId(String tagUuid) throws TagServiceException {

        ResultSet resultSet;
        boolean status = false;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false);
             PreparedStatement prepStmt = connection.prepareStatement(
                     TagMgtConstants.SQLConstants.IS_IDN_TAG_ID_VALID_SQL)) {
            prepStmt.setString(1, tagUuid);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getBoolean(1)) {
                    status = true;
                }
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_TAG_EXISTENCE.getCode(),
                    e.getMessage());
        }
        return status;
    }

    @Override
    public boolean isExistingTag(String name, String type, String tenantUuid) throws TagServiceException {

        ResultSet resultSet;
        boolean status = false;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false)) {
            PreparedStatement prepStmt =
                    connection.prepareStatement(TagMgtConstants.SQLConstants.IS_IDN_TAG_EXISTING_SQL);
            prepStmt.setString(1, name);
            prepStmt.setString(2, type);
            prepStmt.setString(3, tenantUuid);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getBoolean(1)) {
                    status = true;
                }
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_TAG_EXISTENCE.getCode(),
                    e.getMessage());
        }
        return status;
    }

    @Override
    public void deleteTagsByTenant(String tenantUuid) throws TagServiceException {

        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(TagMgtConstants.SQLConstants.DELETE_IDN_TAGS_BY_TENANT_SQL);
            prepStmt.setString(1, tenantUuid);
            prepStmt.execute();
            IdentityDatabaseUtil.commitTransaction(connection);
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_DELETING_TAGS.getCode(),
                    ErrorMessage.ERROR_CODE_ERROR_DELETING_TAGS.getMessage(), e);
        }
    }

    private String resolveSQLFilter(String filter) {

        // To avoid any issues when the filter string is blank or null, assigning "%" to SQLFilter.
        String sqlFilter = "%";
        if (StringUtils.isNotBlank(filter)) {
            sqlFilter = filter.trim().replace("*", "%");
        }
        return sqlFilter;
    }

    /**
     * Convert String value of the db stored is_publicly_visible attribute into boolean.
     *
     * @param isPublic Public visibility of the tag.
     * @return The boolean value of the input String.
     */
    private boolean isPublic(String isPublic) {

        return isPublic.equals("1");
    }
}
