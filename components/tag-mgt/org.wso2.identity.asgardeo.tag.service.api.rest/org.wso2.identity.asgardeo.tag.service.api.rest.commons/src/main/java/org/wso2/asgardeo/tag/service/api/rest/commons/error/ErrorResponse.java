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

package org.wso2.asgardeo.tag.service.api.rest.commons.error;

import org.apache.commons.logging.Log;

/**
 * ErrorResponse class for all API related errors.
 */
public class ErrorResponse extends ErrorDTO {

    private static final long serialVersionUID = -3502358623560083025L;

    /**
     * ErrorResponse Builder.
     */
    public static class Builder {

        private String code;
        private String message;
        private String description;

        public Builder() {

        }

        public Builder withCode(String code) {

            this.code = code;
            return this;

        }

        public Builder withMessage(String message) {

            this.message = message;
            return this;

        }

        public Builder withDescription(String description) {

            this.description = description;
            return this;
        }

        public ErrorResponse build() {

            ErrorResponse error = new ErrorResponse();
            error.setCode(this.code);
            error.setMessage(this.message);
            error.setDescription(this.description);
            return error;
        }

        public ErrorResponse build(Log log, Exception e, String message, boolean isClientException) {

            ErrorResponse error = build();
            String errorMessageFormat = "errorCode: %s | message: %s";
            String errorMsg = String.format(errorMessageFormat, error.getCode(), message);
            if (isClientException) {
                if (log.isDebugEnabled()) {
                    log.debug(errorMsg, e);
                }
            } else {
                log.error(errorMsg, e);
            }
            return error;
        }

        public ErrorResponse build(Log log, String message, boolean isClientException) {

            ErrorResponse error = build();
            String errorMessageFormat = "errorCode: %s | message: %s";
            String errorMsg = String.format(errorMessageFormat, error.getCode(), message);
            if (isClientException && log.isDebugEnabled()) {
                log.debug(errorMsg);
            } else {
                log.error(errorMsg);
            }
            return error;
        }
    }
}

