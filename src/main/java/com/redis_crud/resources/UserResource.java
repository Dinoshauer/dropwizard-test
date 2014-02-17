package com.redis_crud.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis_crud.dao.UserDao;
import com.redis_crud.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

/**
 * Created by kasper on 2/14/14.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDao user;

    public UserResource (UserDao user) {
        this.user = user;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser (String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDto userDto = user.create(objectMapper.readValue(body, UserDto.class));
            URI uri = URI.create(userDto.getEmail());
            return Response.created(uri).entity(userDto).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser (@PathParam("email") String email) {
        try {
            return Response.ok(user.read(email),
                    MediaType.APPLICATION_JSON_TYPE).build();
        } catch (NullPointerException e) {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser (@PathParam("email") String email, String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Response.ok(user.update(email, objectMapper.readValue(body, UserDto.class)),
                    MediaType.APPLICATION_JSON_TYPE).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser (@PathParam("email") String email) {
        if (user.delete(email)) {
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }
}
