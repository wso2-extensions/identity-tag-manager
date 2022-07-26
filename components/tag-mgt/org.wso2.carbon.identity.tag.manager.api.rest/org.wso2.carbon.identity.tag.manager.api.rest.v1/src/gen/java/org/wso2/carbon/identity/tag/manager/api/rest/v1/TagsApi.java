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

package org.wso2.carbon.identity.tag.manager.api.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.ApplicationAssociationRequest;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.ApplicationAssociationResponse;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.Error;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.PatchApplicationAssociation;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagCreateRequest;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagListResponse;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagResponse;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagTypeListResponse;
import org.wso2.carbon.identity.tag.manager.api.rest.v1.model.TagUpdateRequest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.swagger.annotations.*;

import javax.validation.constraints.*;

@Path("/tags")
@Api(description = "The tags API")

public class TagsApi  {

    @Autowired
    private TagsApiService delegate;

    @Valid
    @POST
    @Path("/applications/{application-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Associate tags with the application. ", notes = "This API provides the capability to associate the application with the given set of tags.   **NOTE:**  * Only the application tags can be associated using this API. * Application Update permissions are required.     <b>Permission required:</b>   * /permission/admin/manage/identity/applicationmgt/update    <b>Scope required:</b> <br>   * internal_application_mgt_update ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Application-Tag Associations", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful response", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "Conflict", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response associateWithApplication(@ApiParam(value = "Application uuid.",required=true) @PathParam("application-id") String applicationId, @ApiParam(value = "This represents the request to associate an application with a tag." ,required=true) @Valid ApplicationAssociationRequest applicationAssociationRequest) {

        return delegate.associateWithApplication(applicationId,  applicationAssociationRequest );
    }

    @Valid
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create a tag. ", notes = "This API provides the capability to store the tag information that is provided by users.    <b>Permission required:</b>   * /permission/admin/manage/identity/tagmgt/create    <b>Scope required:</b>   * internal_tag_mgt_create ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Tags", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful response", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "Conflict", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response createTag(@ApiParam(value = "This represents the request to create a tag." ,required=true) @Valid TagCreateRequest tagCreateRequest) {

        return delegate.createTag(tagCreateRequest );
    }

    @Valid
    @DELETE
    @Path("/{tag-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete tag by ID. ", notes = "This API provides the capability to delete a tag by the unique identifier.  <b>Permission required:</b>   * /permission/admin/manage/identity/tagmgt/delete  <b>Scope required:</b> <br> * internal_tag_mgt_delete ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Tags", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Successfully Deleted", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response deleteTag(@ApiParam(value = "Tag uuid.",required=true) @PathParam("tag-id") String tagId) {

        return delegate.deleteTag(tagId );
    }

    @Valid
    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Filter tags. ", notes = "This API provides the capability to retrieve a filtered list of tags by name and tag type. If the pagination parameters are not provided, the default values will be used.    <b>Permission required:</b>   * /permission/admin/manage/identity/tagmgt/view    <b>Scope required:</b>   * internal_tag_mgt_view ", response = TagListResponse.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Tags", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = TagListResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response filterTags(    @Valid @Min(1)@ApiParam(value = "Maximum number of records to return. ", defaultValue="10") @DefaultValue("10")  @QueryParam("limit") Integer limit,     @Valid@ApiParam(value = "Number of records to skip for pagination. ", defaultValue="0") @DefaultValue("0")  @QueryParam("offset") Integer offset,     @Valid@ApiParam(value = "Condition to filter the retrieval of records. Supports 'sw', 'co', 'ew' and 'eq' operations. Currently supports filtering based on the 'name' attribute. /tags?filter=name+co+test&filter=type+eq+APPLICATION ")  @QueryParam("filter") String filter) {

        return delegate.filterTags(limit,  offset,  filter );
    }

    @Valid
    @GET
    @Path("/applications/{application-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get associated tags for the application. ", notes = "This API provides the capability to retrieve the tags associated with the given application.  **NOTE:**  * Application view permissions are required.     <b>Permission required:</b>   * /permission/admin/manage/identity/applicationmgt/view    <b>Scope required:</b> <br>   * internal_application_mgt_view ", response = ApplicationAssociationResponse.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Application-Tag Associations", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = ApplicationAssociationResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response getApplicationTags(@ApiParam(value = "Application uuid.",required=true) @PathParam("application-id") String applicationId) {

        return delegate.getApplicationTags(applicationId );
    }

    @Valid
    @GET
    @Path("/{tag-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get tag by ID. ", notes = "This API provides the capability to retrieve a tag by the unique identifier.    <b>Permission required:</b>   * /permission/admin/manage/identity/tagmgt/view    <b>Scope required:</b> <br>   * internal_tag_mgt_view ", response = TagResponse.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Tags", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = TagResponse.class),
        @ApiResponse(code = 400, message = "Invalid tag Ids.", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized.", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = Void.class),
        @ApiResponse(code = 500, message = "Server Error.", response = Error.class)
    })
    public Response getTag(@ApiParam(value = "Tag uuid.",required=true) @PathParam("tag-id") String tagId) {

        return delegate.getTag(tagId );
    }

    @Valid
    @GET
    @Path("/type")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "List tag types. ", notes = "This API provides the capability to retrieve the list of tag types supported by the server.    <b>Permission required:</b>   * /permission/admin/manage/identity/tagmgt/view    <b>Scope required:</b>   * internal_tag_mgt_view ", response = TagTypeListResponse.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Tags", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = TagTypeListResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response getTagsTypes(    @Valid @Min(1)@ApiParam(value = "Maximum number of records to return. ", defaultValue="10") @DefaultValue("10")  @QueryParam("limit") Integer limit,     @Valid@ApiParam(value = "Number of records to skip for pagination. ", defaultValue="0") @DefaultValue("0")  @QueryParam("offset") Integer offset) {

        return delegate.getTagsTypes(limit,  offset );
    }

    @Valid
    @PATCH
    @Path("/{tag-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Partially update tag by ID. ", notes = "This API provides the capability to update a tag by the unique identifier.  <b>Permission required:</b>   * /permission/admin/manage/identity/tagmgt/update  <b>Scope required:</b> <br> * internal_tag_mgt_update ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Tags", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully Updated", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response patchTag(@ApiParam(value = "Tag uuid.",required=true) @PathParam("tag-id") String tagId, @ApiParam(value = "This represents the tag update request" ,required=true) @Valid TagUpdateRequest tagUpdateRequest) {

        return delegate.patchTag(tagId,  tagUpdateRequest );
    }

    @Valid
    @PATCH
    @Path("/applications/{application-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Add/remove bulk tag-application associations. ", notes = "This API provides the capability to add/remove associations of an application with the given set of tags.   **NOTE:**  * Application Update permissions are required.     <b>Permission required:</b>   * /permission/admin/manage/identity/applicationmgt/update    <b>Scope required:</b> <br>   * internal_application_mgt_update ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Application-Tag Associations" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully updated", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response patchTagApplicationAssociations(@ApiParam(value = "Application uuid.",required=true) @PathParam("application-id") String applicationId, @ApiParam(value = "This represents the request to add/delete the associations of an application with tags." ,required=true) @Valid List<PatchApplicationAssociation> patchApplicationAssociation) {

        return delegate.patchTagApplicationAssociations(applicationId,  patchApplicationAssociation );
    }

}
