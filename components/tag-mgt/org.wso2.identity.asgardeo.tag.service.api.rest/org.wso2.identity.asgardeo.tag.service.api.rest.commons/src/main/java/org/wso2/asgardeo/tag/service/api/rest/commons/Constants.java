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

package org.wso2.asgardeo.tag.service.api.rest.commons;

/**
 * Common constants for server APIs.
 */
public class Constants {

    public static final String AUTH_USER_TENANT_DOMAIN = "TenantNameFromContext";
    public static final String TAG_API_PATH_COMPONENT = "/api/asgardeo-tag-mgt";
    public static final String V1_API_PATH_COMPONENT = "/v1";
    public static final String TENANT_CONTEXT_PATH_COMPONENT = "/t/%s";
    public static final String TAG_MANAGEMENT_PATH_COMPONENT ="/tags";
    public static final String ERROR_PREFIX = "ASG-TM-";

    /**
     * Enum for common server error messages.
     */
    public enum ErrorMessages {

        ERROR_COMMON_SERVER_ERROR(
                "10001",
                "Unable to complete operation.",
                "Error occurred while performing operation."),
        ERROR_UNABLE_TO_RETRIEVE_ALL_TAGS(
                "10002",
                "Unable to retrieve tags.",
                "Error occurred while trying to retrieve tags."),
        ERROR_UNABLE_TO_STORE_TAG(
                "10003",
                "Unable to add tag.",
                "Error occurred while trying to storing tag."),
        ERROR_UNABLE_TO_REMOVE_TAG(
                "10004",
                "Unable to remove tag",
                "Error occurred while trying to remove tag: %s"),
        ERROR_UNABLE_TO_RETRIEVE_TAG(
                "10004",
                "Unable to retrieve tag",
                "Error occurred while trying to retrieve tag: %s"),
        ERROR_UNABLE_TO_UPDATE_TAG(
                "10005",
                "Unable to update tag details",
                "Error occurred while updating the tag details."),
        ERROR_UNABLE_TO_STORE_ASSOCIATION(
                "10006",
                "Unable to add tag-application association.",
                "Error occurred while trying to store tag-application association."),
        ERROR_UNABLE_TO_REMOVE_ASSOCIATION(
                "10007",
                "Unable to delete tag-application association.",
                "Error occurred while trying to delete tag-application association."),
        ERROR_UNABLE_TO_GET_ASSOCIATION(
                "10008",
                "Unable to get tag-application association.",
                "Error occurred while trying to get tag-application association."),
        INVALID_FILTER_OPERATION("60004",
                "Attempted filtering operation is invalid.",
                "Attempted filtering operation '%s' is invalid. "
                        +
                        "Please use one of the supported filtering operations such as 'eq', 'co', 'sw', 'ew', 'and' " +
                        "or 'or'.");

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
