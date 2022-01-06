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

package org.wso2.identity.asgardeo.tag.service.constant;

/**
 * This class contains the constants related to tag management.
 */
public class Constants {

    public static final String UIM_ERROR_PREFIX = "ASG-TM-";

    /**
     * This class contains the sql constants.
     */
    public static class SQLConstants {

        public static final String ADD_IDN_TAG_SQL = "INSERT INTO IDN_TAG "
                + "(UUID, NAME, DESCRIPTION, IS_PUBLICLY_VISIBLE, TYPE, TENANT_UUID) VALUES (?,?,?,?,?,?)";

        public static final String UPDATE_IDN_TAG_SQL = "UPDATE IDN_TAG SET NAME = ? , DESCRIPTION = ?, "
                + "IS_PUBLICLY_VISIBLE=? WHERE UUID = ? AND TENANT_UUID = ?";

        public static final String DELETE_IDN_TAG_SQL = "DELETE FROM IDN_TAG WHERE UUID = ? AND TENANT_UUID = ?";

        public static final String ADD_TAG_RESOURCE_ASC_SQL = "INSERT INTO IDN_TAG_ASCS(TAG_UUID, RESOURCE_UUID, "
                + "TENANT_UUID) VALUES (?,?,?)";

        public static final String DELETE_TAG_RESOURCE_ASC_SQL = "DELETE FROM IDN_TAG_ASCS WHERE TAG_UUID= ? "
                + "AND RESOURCE_UUID=?";

        public static final String DELETE_TAG_RESOURCE_ASC_BY_RESOURCE_SQL = "DELETE FROM IDN_TAG_ASCS "
                + "WHERE RESOURCE_UUID = ?";

        public static final String GET_TAG_RESOURCE_ASC_BY_RESOURCE_UUID_SQL = "SELECT * FROM IDN_TAG AS T "
                + "INNER JOIN IDN_TAG_ASCS AS A ON T.UUID = A.TAG_UUID WHERE A.RESOURCE_UUID = ?";

        public static final String GET_IDN_TAG_BY_TAG_UUID_SQL = "SELECT * FROM IDN_TAG WHERE UUID = ?";

        public static final String GET_IDN_TAG_BY_NAME_TYPE_TENANT_UUID_SQL = "SELECT UUID, DESCRIPTION ,"
                + "IS_PUBLICLY_VISIBLE FROM IDN_TAG WHERE NAME = ? AND TYPE = ? AND TENANT_UUID = ?";

        public static final String LOAD_IDN_TAGS_BY_TYPE_NAME_TENANT_UUID = "SELECT UUID, NAME, DESCRIPTION ,"
                + "IS_PUBLICLY_VISIBLE FROM IDN_TAG WHERE TENANT_UUID = ? AND NAME LIKE ? LIMIT ? OFFSET ?";

        public static final String LOAD_IDN_TAGS_BY_TYPE_TENANT_UUID = "SELECT UUID, NAME, DESCRIPTION ,"
                + "IS_PUBLICLY_VISIBLE FROM IDN_TAG WHERE TENANT_UUID = ? AND TYPE LIKE ? LIMIT ? OFFSET ?";
    }
}
