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

package org.wso2.carbon.identity.tag.manager.api.rest.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;
import javax.validation.Valid;

public class TagListItem  {
  
    private String id;
    private String name;
    private Boolean isPublic;

    /**
    * Unique identifier of the tag.
    **/
    public TagListItem id(String id) {

        this.id = id;
        return this;
    }
    
    @ApiModelProperty(example = "85e3f4b8-0d22-4181-b1e3-1651f71b88bd", value = "Unique identifier of the tag.")
    @JsonProperty("id")
    @Valid
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
    * Name of the tag.
    **/
    public TagListItem name(String name) {

        this.name = name;
        return this;
    }
    
    @ApiModelProperty(example = "example-tag", value = "Name of the tag.")
    @JsonProperty("name")
    @Valid
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
    * Whether the tag is visible to the end-user.
    **/
    public TagListItem isPublic(Boolean isPublic) {

        this.isPublic = isPublic;
        return this;
    }
    
    @ApiModelProperty(example = "true", value = "Whether the tag is visible to the end-user.")
    @JsonProperty("isPublic")
    @Valid
    public Boolean getIsPublic() {
        return isPublic;
    }
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }



    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TagListItem tagListItem = (TagListItem) o;
        return Objects.equals(this.id, tagListItem.id) &&
            Objects.equals(this.name, tagListItem.name) &&
            Objects.equals(this.isPublic, tagListItem.isPublic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isPublic);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class TagListItem {\n");
        
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    isPublic: ").append(toIndentedString(isPublic)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
    private String toIndentedString(java.lang.Object o) {

        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n");
    }
}

