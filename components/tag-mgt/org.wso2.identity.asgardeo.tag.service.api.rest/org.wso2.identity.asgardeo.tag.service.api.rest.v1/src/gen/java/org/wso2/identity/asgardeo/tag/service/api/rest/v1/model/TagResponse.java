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

package org.wso2.identity.asgardeo.tag.service.api.rest.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;

public class TagResponse  {
  
    private String id;
    private String name;
    private String description;
    private String typeId;
    private Boolean isPublic;

    /**
    * Unique identifier of the tag.
    **/
    public TagResponse id(String id) {

        this.id = id;
        return this;
    }
    
    @ApiModelProperty(example = "scsbcj-2rfjeiu=243434-dvhjduvbjfvd", value = "Unique identifier of the tag.")
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
    public TagResponse name(String name) {

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
    * Description about the tag.
    **/
    public TagResponse description(String description) {

        this.description = description;
        return this;
    }
    
    @ApiModelProperty(example = "This is an example tag", value = "Description about the tag.")
    @JsonProperty("description")
    @Valid
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
    * Unique identifier of the type of the tag.
    **/
    public TagResponse typeId(String typeId) {

        this.typeId = typeId;
        return this;
    }
    
    @ApiModelProperty(example = "djhfvbjhdhjvbjhdf-eub3fhj-24376f3rf", value = "Unique identifier of the type of the tag.")
    @JsonProperty("typeId")
    @Valid
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
    * Whether the tag is visible to the end-user.
    **/
    public TagResponse isPublic(Boolean isPublic) {

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
        TagResponse tagResponse = (TagResponse) o;
        return Objects.equals(this.id, tagResponse.id) &&
            Objects.equals(this.name, tagResponse.name) &&
            Objects.equals(this.description, tagResponse.description) &&
            Objects.equals(this.typeId, tagResponse.typeId) &&
            Objects.equals(this.isPublic, tagResponse.isPublic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, typeId, isPublic);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class TagResponse {\n");
        
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    typeId: ").append(toIndentedString(typeId)).append("\n");
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

