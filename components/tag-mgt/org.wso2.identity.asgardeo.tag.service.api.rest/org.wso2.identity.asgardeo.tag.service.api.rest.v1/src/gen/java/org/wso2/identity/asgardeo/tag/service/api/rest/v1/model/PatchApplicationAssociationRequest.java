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

package org.wso2.identity.asgardeo.tag.service.api.rest.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;

public class PatchApplicationAssociationRequest  {
  

@XmlType(name="OpEnum")
@XmlEnum(String.class)
public enum OpEnum {

    @XmlEnumValue("add") ADD(String.valueOf("add")), @XmlEnumValue("remove") REMOVE(String.valueOf("remove"));


    private String value;

    OpEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static OpEnum fromValue(String value) {
        for (OpEnum b : OpEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

    private OpEnum op;
    private List<String> tagIds = null;


    /**
    **/
    public PatchApplicationAssociationRequest op(OpEnum op) {

        this.op = op;
        return this;
    }
    
    @ApiModelProperty(value = "")
    @JsonProperty("op")
    @Valid
    public OpEnum getOp() {
        return op;
    }
    public void setOp(OpEnum op) {
        this.op = op;
    }

    /**
    **/
    public PatchApplicationAssociationRequest tagIds(List<String> tagIds) {

        this.tagIds = tagIds;
        return this;
    }
    
    @ApiModelProperty(value = "")
    @JsonProperty("tagIds")
    @Valid
    public List<String> getTagIds() {
        return tagIds;
    }
    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    public PatchApplicationAssociationRequest addTagIdsItem(String tagIdsItem) {
        if (this.tagIds == null) {
            this.tagIds = new ArrayList<>();
        }
        this.tagIds.add(tagIdsItem);
        return this;
    }

    

    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatchApplicationAssociationRequest patchApplicationAssociationRequest = (PatchApplicationAssociationRequest) o;
        return Objects.equals(this.op, patchApplicationAssociationRequest.op) &&
            Objects.equals(this.tagIds, patchApplicationAssociationRequest.tagIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(op, tagIds);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class PatchApplicationAssociationRequest {\n");
        
        sb.append("    op: ").append(toIndentedString(op)).append("\n");
        sb.append("    tagIds: ").append(toIndentedString(tagIds)).append("\n");
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

