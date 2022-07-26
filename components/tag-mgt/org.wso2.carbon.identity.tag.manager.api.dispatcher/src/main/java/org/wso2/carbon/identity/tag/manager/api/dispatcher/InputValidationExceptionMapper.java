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

package org.wso2.carbon.identity.tag.manager.api.dispatcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.error.ErrorDTO;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.error.ErrorResponse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Map input validation exceptions.
 */
public class InputValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Log log = LogFactory.getLog(InputValidationExceptionMapper.class);

    private static final String ERROR_CODE = "IDN-TE-10000";
    private static final String ERROR_MESSAGE = "Invalid Request";
    private static final String ERROR_DESCRIPTION = "Provided request body content is not in the expected format.";

    @Override
    public Response toResponse(ConstraintViolationException e) {

        StringBuilder description = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        for (ConstraintViolation constraintViolation : constraintViolations) {
            if (StringUtils.isNotBlank(description)) {
                description.append(" ");
            }
            description.append(constraintViolation.getMessage());
        }

        if (StringUtils.isBlank(description)) {
            description = new StringBuilder(ERROR_DESCRIPTION);
        }

        ErrorDTO errorDTO = new ErrorResponse.Builder()
                .withCode(ERROR_CODE)
                .withMessage(ERROR_MESSAGE)
                .withDescription(description.toString())
                .build(log, e.getMessage(), true);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
    }
}
