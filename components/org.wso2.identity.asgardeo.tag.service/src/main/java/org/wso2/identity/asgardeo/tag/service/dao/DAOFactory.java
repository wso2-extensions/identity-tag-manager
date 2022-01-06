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

package org.wso2.identity.asgardeo.tag.service.dao;

/**
 * Tag management service DAO factory.
 */
public class DAOFactory {

    private static DAOFactory factory = new DAOFactory();
    private TagManagementDAO tagManagementDAO;

    private DAOFactory() {

        this.tagManagementDAO = new TagManagementDAOImpl();
    }

    public static DAOFactory getInstance() {

        return factory;
    }

    /**
     * Get the tag management DAO.
     *
     * @return Tag management DAO.
     */
    public TagManagementDAO getTagManagementDAO() {

        return tagManagementDAO;
    }

}
