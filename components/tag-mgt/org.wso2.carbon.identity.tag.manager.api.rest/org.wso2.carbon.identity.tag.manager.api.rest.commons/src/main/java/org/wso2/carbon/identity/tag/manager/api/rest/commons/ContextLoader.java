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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.error.APIError;
import org.wso2.carbon.identity.tag.manager.api.rest.commons.error.ErrorResponse;
import org.wso2.carbon.identity.core.ServiceURLBuilder;
import org.wso2.carbon.identity.core.URLBuilderException;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.core.util.IdentityUtil;

import java.net.URI;

import javax.ws.rs.core.Response;

import static org.wso2.carbon.identity.tag.manager.api.rest.commons.Constants.TAG_API_PATH_COMPONENT;
import static org.wso2.carbon.identity.tag.manager.api.rest.commons.Constants.TENANT_CONTEXT_PATH_COMPONENT;
import static org.wso2.carbon.identity.tag.manager.model.ErrorMessage.ERROR_CODE_DEFAULT_UNEXPECTED_ERROR;

/**
 * Load information from context.
 */
public class ContextLoader {

    private static final Log LOG = LogFactory.getLog(ContextLoader.class);

    public static String getTenantDomainFromAuthUser() {

        String tenantDomain = null;
        if (IdentityUtil.threadLocalProperties.get().get(Constants.AUTH_USER_TENANT_DOMAIN) != null) {
            tenantDomain = (String) IdentityUtil.threadLocalProperties.get().get(Constants.AUTH_USER_TENANT_DOMAIN);
        }
        return tenantDomain;
    }

    /**
     * Build the complete URI prepending the tag API context without the proxy context path, to the endpoint.
     * Ex: https://localhost:9443/t/<tenant-domain>/identity-tag-mgt/v1/<endpoint>
     *
     * @param endpoint relative endpoint path.
     * @return Fully qualified and complete URI.
     */
    public static URI buildURIForHeader(String endpoint) {

        URI loc;
        String context = getContext(endpoint);

        try {
            String url = ServiceURLBuilder.create().addPath(context).build().getAbsolutePublicURL();
            loc = URI.create(url);
        } catch (URLBuilderException e) {
            String errorDescription = "Server encountered an error while building URL for response header.";
            throw buildInternalServerError(e, errorDescription);
        }
        return loc;
    }

    private static String getContext(String endpoint) {

        String context;
        if (IdentityTenantUtil.isTenantQualifiedUrlsEnabled()) {
            context = TAG_API_PATH_COMPONENT + endpoint;
        } else {
            context = String.format(TENANT_CONTEXT_PATH_COMPONENT, getTenantDomainFromAuthUser()) +
                    TAG_API_PATH_COMPONENT + endpoint;
        }
        return context;
    }

    /**
     * Builds APIError to be thrown if the URL building fails.
     *
     * @param e                Exception occurred while building the URL.
     * @param errorDescription Description of the error.
     * @return APIError object which contains the error description.
     */
    private static APIError buildInternalServerError(Exception e, String errorDescription) {

        String errorCode = ERROR_CODE_DEFAULT_UNEXPECTED_ERROR.getCode();
        String errorMessage = "Error while building response.";
        ErrorResponse errorResponse = new ErrorResponse.Builder().withCode(errorCode).withMessage(errorMessage)
                .withDescription(errorDescription).build(LOG, errorMessage, true);
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        return new APIError(status, errorResponse);
    }
}
