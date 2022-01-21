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

package org.wso2.carbon.identity.tag.manager.api.rest.commons;

/**
 * Common constants for server APIs.
 */
public class Constants {

    public static final String AUTH_USER_TENANT_DOMAIN = "TenantNameFromContext";
    public static final String TAG_API_PATH_COMPONENT = "/api/identity-tag-mgt";
    public static final String V1_API_PATH_COMPONENT = "/v1";
    public static final String TENANT_CONTEXT_PATH_COMPONENT = "/t/%s";
    public static final String TAG_MANAGEMENT_PATH_COMPONENT = "/tags";
    public static final String ERROR_PREFIX = "TM-";

    /**
     * Enum for common server error messages.
     */
    public enum ErrorMessages {

        ERROR_CODE_EMPTY_TAG_UUID("60000", "Empty tag UUID",
                "Tag UUID not specified in the request"),
        ERROR_CODE_EMPTY_TAG_NAME("60001", "Empty tag name",
                "Tag name not specified in the request"),
        ERROR_CODE_EMPTY_TAG_TYPE("60002", "Empty tag type",
                "Tag type not specified in the request"),
        ERROR_CODE_INVALID_TAG_NAME("60003", "Invalid tag name",
                "Tag name is not in the expected format"),
        INVALID_FILTER_OPERATION("60004",
                "Attempted filtering operation is invalid.",
                "Attempted filtering operation '%s' is invalid. "
                        +
                        "Please use one of the supported filtering operations such as 'eq', 'co', 'sw', 'ew', 'and' " +
                        "or 'or'."),
        ERROR_COMMON_SERVER_ERROR(
                "65000",
                "Unable to complete operation.",
                "Error occurred while performing operation."),
        ERROR_UNABLE_TO_RETRIEVE_ALL_TAGS(
                "65001",
                "Unable to retrieve tags.",
                "Error occurred while trying to retrieve tags."),
        ERROR_UNABLE_TO_STORE_TAG(
                "65003",
                "Unable to add tag.",
                "Error occurred while trying to storing tag."),
        ERROR_UNABLE_TO_REMOVE_TAG(
                "65004",
                "Unable to remove tag",
                "Error occurred while trying to remove tag: %s"),
        ERROR_UNABLE_TO_RETRIEVE_TAG(
                "65005",
                "Unable to retrieve tag",
                "Error occurred while trying to retrieve tag: %s"),
        ERROR_UNABLE_TO_UPDATE_TAG(
                "65006",
                "Unable to update tag details",
                "Error occurred while updating the tag details."),
        ERROR_UNABLE_TO_STORE_ASSOCIATION(
                "65007",
                "Unable to add tag-resource association.",
                "Error occurred while trying to store tag-resource association."),
        ERROR_UNABLE_TO_REMOVE_ASSOCIATION(
                "65008",
                "Unable to delete tag-resource association.",
                "Error occurred while trying to delete tag-resource association."),
        ERROR_UNABLE_TO_GET_ASSOCIATION(
                "65009",
                "Unable to get tag-resource association.",
                "Error occurred while trying to get tag-resource association."),
        ERROR_UNABLE_TO_GET_TAG_TYPES(
                "65010",
                "Unable to get types.",
                "Error occurred while trying to get a list of tag types.");

        private final String code;
        private final String message;
        private final String description;

        ErrorMessages(String code, String message, String description) {

            this.code = code;
            this.message = message;
            this.description = description;
        }

        public String getCode() {

            return ERROR_PREFIX + code;
        }

        public String getMessage() {

            return message;
        }

        public String getDescription() {

            return description;
        }

        @Override
        public String toString() {

            return code + " | " + message;
        }
    }
}
