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
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;

public class TagUpdateRequest  {
  
    private String name;
    private String description;
    private Boolean isPublic;

    /**
    * Unique identifier of the tag.
    **/
    public TagUpdateRequest name(String name) {

        this.name = name;
        return this;
    }
    
    @ApiModelProperty(example = "example-tag.", value = "Unique identifier of the tag.")
    @JsonProperty("name")
    @Valid
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
    * Description about the tag.
    **/
    public TagUpdateRequest description(String description) {

        this.description = description;
        return this;
    }
    
    @ApiModelProperty(example = "This is an example tag.", value = "Description about the tag.")
    @JsonProperty("description")
    @Valid
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
    * Whether the tag is visible to the end-user.
    **/
    public TagUpdateRequest isPublic(Boolean isPublic) {

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
        TagUpdateRequest tagUpdateRequest = (TagUpdateRequest) o;
        return Objects.equals(this.name, tagUpdateRequest.name) &&
            Objects.equals(this.description, tagUpdateRequest.description) &&
            Objects.equals(this.isPublic, tagUpdateRequest.isPublic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, isPublic);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class TagUpdateRequest {\n");
        
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

