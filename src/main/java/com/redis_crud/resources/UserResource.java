package com.redis_crud.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis_crud.dto.UserDto;
import redis.clients.jedis.Jedis;

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
    private final Jedis jedis;

    public UserResource (Jedis jedis) {
        this.jedis = jedis;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser (String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserDto user = objectMapper.readValue(body, UserDto.class);
            jedis.set(user.getEmail(), user.getName());
            URI uri = URI.create(user.getEmail());
            return Response.created(uri).entity(user).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser (@PathParam("email") String email) {
        try {
            String name = jedis.get(email);
            UserDto user = new UserDto();
            if (!name.isEmpty()) {
                user.setName(name);
                user.setEmail(email);
            }
            return Response.ok(user, MediaType.APPLICATION_JSON_TYPE).build();
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
            UserDto user = objectMapper.readValue(body, UserDto.class);
            if (user.getEmail() != null) {
                jedis.rename(email, user.getEmail());
                email = user.getEmail();
            } else {
                user.setEmail(email);
            }
            jedis.set(email, user.getName());
            return Response.ok(user, MediaType.APPLICATION_JSON_TYPE).build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser (@PathParam("email") String email) {
        Long result = jedis.del(email);
        if (result != 0) {
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }
}
