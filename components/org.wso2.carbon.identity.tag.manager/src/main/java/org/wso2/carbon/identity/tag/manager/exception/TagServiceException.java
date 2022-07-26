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

package org.wso2.carbon.identity.tag.manager.exception;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.tag.manager.model.ErrorMessage;

/**
 * This class contains the implementation of TagServiceException.
 */
public class TagServiceException extends IdentityException {

    private static final long serialVersionUID = 1700334648018222520L;

    /**
     * Constructs a new exception with an error code and a detail message.
     *
     * @param errorCode The error code.
     * @param message   The detail message.
     */
    public TagServiceException(String errorCode, String message) {

        super(errorCode, message);
        this.setErrorCode(errorCode);
    }

    /**
     * Constructs a new exception with an error code, detail message and throwable.
     *
     * @param errorCode The error code.
     * @param message   The detail message.
     * @param throwable Throwable.
     */
    public TagServiceException(String errorCode, String message, Throwable throwable) {

        super(errorCode, message, throwable);
        this.setErrorCode(errorCode);
    }

    /**
     * Get the default error code of the exception.
     *
     * @return Error description.
     */
    private String getDefaultErrorCode() {

        String errorCode = super.getErrorCode();
        if (StringUtils.isEmpty(errorCode)) {
            errorCode = ErrorMessage.ERROR_CODE_DEFAULT_UNEXPECTED_ERROR.getCode();
            return errorCode;
        }
        return errorCode;
    }
}
