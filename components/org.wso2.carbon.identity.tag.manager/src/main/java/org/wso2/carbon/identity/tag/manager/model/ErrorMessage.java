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

package org.wso2.carbon.identity.tag.manager.model;

/**
 * This class contains the error codes for the error scenarios.
 */
public enum ErrorMessage {

    ERROR_CODE_DEFAULT_BAD_REQUEST("60000", "Bad Request", "Bad Request"),
    ERROR_CODE_DEFAULT_UNEXPECTED_ERROR("65000", "Unexpected Error",
            "Unexpected error occurred"),
    ERROR_CODE_DEFAULT_SERVER_ERROR("65001", "Internal Server Error",
            "Internal Server Error"),

    // Client Errors.
    ERROR_CODE_EMPTY_TAG_ID("60001", "Empty Tag ID",
            "Tag ID cannot be empty"),
    ERROR_CODE_INVALID_LIMIT_VALUE("60002", "Invalid limit value",
            "Limit need to be equal or greater than 0"),
    ERROR_CODE_INVALID_OFFSET_VALUE("60003", "Invalid offset value",
            "Offset need to be equal or greater than 0"),
    ERROR_INVALID_TENANT_ID("60004", "Invalid tenant id: %s.",
            "Invalid Tenant ID"),
    ERROR_CODE_EMPTY_TENANT_UUID("60005", "Empty Tenant UUID",
            "Tenant UUID cannot be empty"),
    ERROR_CODE_INVALID_TAG_UUID("60006", "Empty tag UUID",
            "Tag UUID cannot be empty"),
    ERROR_CODE_INVALID_ASSOCIATION("60007", "No such association",
            "No matching association for the given tag and resource UUID"),
    ERROR_CODE_EMPTY_TAG_UUID("60008", "Empty tag UUID",
            "Tag UUID cannot be empty"),
    ERROR_CODE_EMPTY_NAME("60009", "Empty name",
            "Tag name cannot be empty"),
    ERROR_CODE_EMPTY_RESOURCE_UUID("60010", "Empty resource UUID",
            "Resource UUID cannot be empty"),
    ERROR_CODE_TAG_ALREADY_EXISTS("60011", "Tag named %s for tenant %s already exists.",
            "Tag name should be unique for a given type and tenant"),
    ERROR_CODE_TAG_DATA_DOES_NOT_EXIST("60012", "Tag with UUID %s does not exist.",
            "Tag UUID not found"),
    UNSUPPORTED_FILTER_ATTRIBUTE("60013", "Filtering using the attempted attribute is not supported.",
            "Filtering cannot be done with the '%s' attribute." +
                    "Filtering is only supported with the 'name' attribute."),
    INVALID_FILTER_FORMAT("60014", "Invalid format tag for filtering.",
            "Filter needs to be in the format <attribute>+<operation>+<value>. Eg: name+eq+test"),
    INVALID_FILTER_OPERATION("60015", "Attempted filtering operation is invalid.",
            "Attempted filtering operation '%s' is invalid. " +
                    "Please use one of the supported filtering operations such as 'eq', 'co', 'sw', 'ew', 'and' " +
                    "or 'or'."),

    // Server Errors.
    ERROR_CODE_SORT_BY_NOT_IMPLEMENTED("65002", "Sort-By not implemented",
            "Sort by option is not implemented"),
    ERROR_CODE_SORT_ORDER_NOT_IMPLEMENTED("65003", "Sort-Order not implemented",
            "Sort order is not implemented"),
    ERROR_CODE_INVALID_DATA("65004", "Invalid data provided",
            "Invalid data provided"),
    ERROR_CODE_ERROR_GETTING_TAGS_FROM_TYPE_TENANT_UUID("65005", "Error loading tags",
            "Error occurred while getting the attributes for the tags"),
    ERROR_CODE_ERROR_LOADING_TAG("65006", "Error loading tags",
            "Error occurred while getting the attributes for the tags"),
    ERROR_CODE_ERROR_LOADING_TAG_TYPES("65007", "Error loading tag types",
            "Error occurred while getting the list of tag types"),
    ERROR_CODE_ERROR_GETTING_ASSOCIATIONS("65008", "Error getting tag-resource associations",
            "Error occurred while all the tag-resource associations"),
    ERROR_CODE_ERROR_GETTING_ASSOCIATION("65009", "Error getting association for tag",
            "Error occurred while getting tag-resource association of the tag: %s , resource: %s"),
    ERROR_CODE_ERROR_ADDING_TAG("65010", "Error while adding tag",
            "Error occurred while adding the tag"),
    ERROR_CODE_ERROR_ADDING_ASSOCIATION("65011", "Error while adding tag-resource association",
            "Error occurred while adding tag-resource association"),
    ERROR_CODE_ERROR_DELETING_ASSOCIATION("65012", "Error while deleting tag-resource association",
            "Error occurred while deleting tag-resource association"),
    ERROR_CODE_ERROR_DELETING_TAG("65013", "Error deleting tag",
            "Error occurred while deleting the tag: %s"),
    ERROR_CODE_ERROR_DELETING_TAGS("65014", "Error deleting tags",
            "Error occurred while deleting the tags for the tenant: %s"),
    ERROR_CODE_ERROR_UPDATING_TAG("65015", "Error while updating tag",
            "Error occurred while updating tag: %s"),
    ERROR_CODE_NO_UNIQUE_ID_FOR_TENANT("65016", "No uniqueId found for the tenant: %s",
            "Error occurred while getting Tenant UUID."),
    ERROR_CODE_ERROR_GETTING_TENANT_UUID("65017", "Error while getting the tenant UUID",
            "Error occurred while getting the tenant UUID for tenant: %s");

    private final String code;
    private final String message;
    private final String description;
    public static final String ERROR_PREFIX = "TM-";

    ErrorMessage(String code, String message, String description) {

        this.code = code;
        this.message = message;
        this.description = description;
    }

    /**
     * Get the error code with the scenario.
     *
     * @return Error code without the scenario prefix.
     */
    public String getCode() {

        return ERROR_PREFIX + code;
    }

    /**
     * Get error message.
     *
     * @return Error scenario message.
     */
    public String getMessage() {

        return message;
    }

    /**
     * Get error scenario message description.
     *
     * @return Error scenario description.
     */
    public String getDescription() {

        return description;
    }

    @Override
    public String toString() {

        return getCode() + " | " + getMessage() + " | " + getDescription();
    }
}
