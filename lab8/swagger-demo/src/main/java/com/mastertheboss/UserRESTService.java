package com.mastertheboss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastertheboss.model.Film;
import com.mastertheboss.model.User;
import com.mastertheboss.repository.exceptions.ElementNotFoundException;
import com.mastertheboss.repository.interfaces.IUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/user")
@Api(value = "User REST service")
public class UserRESTService {

    @EJB(lookup = "java:module/UserRepository")
    private IUserRepository userRepository;

    @GET
    @Path("/osoby")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Redirect", notes = "Redirect to proper site")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Redirect to /users")})
    public Response returnAllUsersRedirect() {
        return Response.status(302).location(URI.create("/user/users")).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns User", notes = "Returns User with specified ID", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful User retrieval", response = User.class),
            @ApiResponse(code = 500, message = "UnSuccessful User retrieval"),
            @ApiResponse(code = 404, message = "Resource with specified URI doesn't exist")
    })
    public Response returnUser(@PathParam("id") Long id) {
        try {
            User user = userRepository.findById(id);
            if (user == null) {
                return Response.status(404).build();
            }
            String serialized = new ObjectMapper().writeValueAsString(user);
            return Response.ok().entity(serialized).build();
        }  catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/users")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns List of Users", notes = "Returns json object with user's data", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful film retrieval", response = List.class),
    })
    public Response returnAllUsers() {
        return Response.ok().entity(userRepository.findAll()).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns User", notes = "Returns User with specified ID", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful user update", response = User.class),
            @ApiResponse(code = 500, message = "UnSuccessful user update"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource with specified URI doesn't exist")
    })
    @Transactional
    public Response updateUser(User user) {
        try {
            if (user.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST) .build();
            }
            userRepository.update(user.getId(), user);
            return Response.ok() .entity(userRepository.findById(user.getId())).build();
        } catch (ElementNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns User", notes = "Returns User with specified ID", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created user", response = User.class),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @Transactional
    public Response createUser(User user) {
        try {
            User dbUser = new User(user);
            userRepository.save(dbUser);
            return Response.status(201).entity(dbUser).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Returns User", notes = "Returns User with specified ID", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted resource", response = User.class),
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Resource with specified URI doesn't exist"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @Transactional
    public Response deleteUser(@QueryParam("id") Long id) {
        try {
            if (id == null) {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .build();
            }
            User toDelete = userRepository.findById(id);
            if (toDelete == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
            userRepository.delete(id);
            return Response.status(204)
                    .entity(toDelete)
                    .build();
        } catch (Exception e) {
            return Response
                    .serverError()
                    .build();
        }
    }

}
