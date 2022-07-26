/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com).
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

package org.wso2.carbon.identity.tag.manager.api.rest.v1.factories;

import org.wso2.carbon.identity.tag.manager.api.rest.v1.TagsApiService;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.impl.TagsApiServiceImpl;

public class TagsApiServiceFactory {

   private final static TagsApiService service = new TagsApiServiceImpl();

   public static TagsApiService getTagsApi()
   {
      return service;
   }
}
