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

package org.wso2.identity.asgardeo.tag.service;

/**
 * Utility methods for tag-application association management service.
 */
public class AscManagerUtil {

    // This will be used to indicate a tag association remove operation.
    private static ThreadLocal<Boolean> isAssociationRemoveOperation = new ThreadLocal<>();

    /**
     * Get whether the flow is a tag association removing operation.
     *
     * @return True if a tag association removing operation is invoked.
     */
    public static boolean isAssociationRemoveOperation() {

        if (isAssociationRemoveOperation == null || isAssociationRemoveOperation.get() == null) {
            return false;
        }
        return isAssociationRemoveOperation.get();
    }

    /**
     * Set whether the flow is a tag association removing operation.
     *
     * @param isAssociationRemoveOperation Whether the flow is a tag association removing operation.
     */
    public static void setAssociationRemoveOperation(boolean isAssociationRemoveOperation) {

        AscManagerUtil.isAssociationRemoveOperation.set(isAssociationRemoveOperation);
    }

    /**
     * Clear whether the flow is a tag association removing operation.
     */
    public static void clearAssociationRemoveOperation() {

        isAssociationRemoveOperation.remove();
    }
}
