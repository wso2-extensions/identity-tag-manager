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
import org.wso2.carbon.identity.tag.manager.core.constant.Constants;
import org.wso2.carbon.identity.tag.manager.exception.TagServiceException;
import org.wso2.carbon.identity.tag.manager.model.ErrorMessage;
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

        PreparedStatement prepStmt;
        ResultSet resultSet;
        Tag tag = null;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.GET_IDN_TAG_BY_TAG_UUID_SQL);
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
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_LOADING_TAG.getCode(), e);
        }
        return tag;
    }

    @Override
    public Tag loadTag(String name, String type, String tenantUuid) throws TagServiceException {

        PreparedStatement prepStmt;
        ResultSet resultSet;
        Tag tag = null;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.GET_IDN_TAG_BY_NAME_TYPE_TENANT_UUID_SQL);
            prepStmt.setString(1, name);
            prepStmt.setString(2, type);
            prepStmt.setString(3, tenantUuid);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                tag = new Tag();
                tag.setTagUuid(resultSet.getString(1));
                tag.setDescription(resultSet.getString(2));
                tag.setPubliclyVisible(isPublic(resultSet.getString(3)));
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_LOADING_TAG.getCode(), e);
        }
        return tag;
    }

    @Override
    public TagListResult filterTags(int limit, int offset, String sortBy, String sortOrder, String filter,
                                    String tenantUuid) throws TagServiceException {

        if (limit == 0) {
            return null;
        }
        PreparedStatement prepStmt;
        ResultSet resultSet;
        TagListResult tagListResult = new TagListResult();
        List<Tag> tagList = new ArrayList<>();
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false)) {

            String filterResolvedForSQL = resolveSQLFilter(filter);

            prepStmt = connection.prepareStatement(Constants.SQLConstants.LOAD_IDN_TAGS_BY_TYPE_NAME_TENANT_UUID);
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
                    e);
        }
        return tagListResult;
    }

    @Override
    public List<String> loadTagTypes(int limit, int offset, String sortBy, String sortOrder)
            throws TagServiceException {

        PreparedStatement prepStmt;
        ResultSet resultSet;
        List<String> tagTypesList=null;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.LOAD_IDN_TAG_TYPES);
            prepStmt.setInt(1, limit);
            prepStmt.setInt(2, offset);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                tagTypesList.add(resultSet.getString(1));
            }
        } catch (SQLException | NullPointerException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_LOADING_TAG_TYPES.getCode(), e);
        }
        return tagTypesList;
    }

    @Override
    public String storeTag(Tag tag, String tenantUuid) throws TagServiceException {

        String tagUuid = UUIDGenerator.generateUUID();
        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.ADD_IDN_TAG_SQL);
            prepStmt.setString(1, tagUuid);
            prepStmt.setString(2, tag.getName());
            prepStmt.setString(3, tag.getDescription());
            prepStmt.setString(4, isPublicToString(tag.isPubliclyVisible()));
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

        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.DELETE_IDN_TAG_SQL);
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

        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.UPDATE_IDN_TAG_SQL);
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

        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.ADD_TAG_RESOURCE_ASC_SQL);
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

        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.DELETE_TAG_RESOURCE_ASC_SQL);
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
        PreparedStatement prepStmt;
        ResultSet rs;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.GET_TAG_RESOURCE_ASC_BY_RESOURCE_UUID_SQL);
            prepStmt.setString(1, resourceUuid);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setTagUuid(rs.getString(1));
                tag.setName(rs.getString(2));
                tag.setPubliclyVisible(isPublic(rs.getString(3)));
                tagList.add(tag);
            }
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_ASSOCIATIONS.getCode(), e);
        }
        return new TagAssociationsResult(tagList.size(), tagList);
    }

    @Override
    public void deleteAssociationByResource(String resourceUuid) throws TagServiceException {

        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.DELETE_TAG_RESOURCE_ASC_BY_RESOURCE_SQL);
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

        PreparedStatement prepStmt;
        boolean status;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.GET_TAG_RESOURCE_ASC_BY_RESOURCE_TAG_UUID_SQL);
            prepStmt.setString(1, tagUuid);
            prepStmt.setString(1, resourceId);
            status = prepStmt.execute();
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_GETTING_ASSOCIATION.getCode(), e);
        }
        return status;
    }

    @Override
    public boolean isExistingTagId(String tagUuid) throws TagServiceException {

        PreparedStatement prepStmt;
        boolean status;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(false)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.GET_IDN_TAG_BY_TAG_UUID_SQL);
            prepStmt.setString(1, tagUuid);
            status = prepStmt.execute();
        } catch (SQLException e) {
            throw new TagServiceException(ErrorMessage.ERROR_CODE_ERROR_LOADING_TAG.getCode(), e);
        }
        return status;
    }

    @Override
    public void deleteTagsByTenant(String tenantUuid) throws TagServiceException {

        PreparedStatement prepStmt;
        try (Connection connection = IdentityDatabaseUtil.getDBConnection(true)) {
            prepStmt = connection.prepareStatement(Constants.SQLConstants.DELETE_IDN_TAGS_BY_TENANT_SQL);
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
            sqlFilter = filter.trim()
                    .replace("*", "%");
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

    /**
     * Convert boolean value of is_publicly_visible into String when storing in the database.
     *
     * @param isPublic Public visibility of the tag.
     * @return The String value of the boolean value.
     */
    private String isPublicToString(boolean isPublic) {

        if (isPublic) {
            return "1";
        }
        return "0";
    }
}
