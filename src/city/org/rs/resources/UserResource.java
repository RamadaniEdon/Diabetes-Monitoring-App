package city.org.rs.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import city.org.rs.AuthenticationFilter;
import city.org.rs.dao.UserDAO;
import city.org.rs.models.User;
import city.org.rs.utils.Helpers;
import city.org.rs.utils.JwtUtil;
import city.org.rs.utils.PasswordUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response listUsers() {
        UserDAO dao = new UserDAO();
        try {
            List<User> users = dao.getAllUsers();
            return Response.ok(users, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving users").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response addUser(User user) {
        UserDAO dao = new UserDAO();
        try {
            dao.addUser(user);
            URI uri = new URI("/users/" + user.getUserId());
            return Response.created(uri).build();
        } catch (SQLException | URISyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding user").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
    public Response updateUser(@PathParam("id") int id, User user) {
        UserDAO dao = new UserDAO();
        try {
            user.setUserId(id);
            dao.updateUser(user);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating user").build();
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
    public Response deleteUser(@PathParam("id") int id) {
        UserDAO dao = new UserDAO();
        try {
            dao.deleteUser(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting user").build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response getUser(@PathParam("id") int id) {
        UserDAO dao = new UserDAO();
        try {
            User user = dao.getUser(id);
            if (user != null) {
                return Response.ok(user, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving user").build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        UserDAO dao = new UserDAO();
        try {
            User userFromDB = dao.getUserByUsername(user.getUsername());
            if(userFromDB != null && PasswordUtil.verifyPassword(user.getPassword(), userFromDB.getPassword())) {
                String token = JwtUtil.createToken(userFromDB.getUsername(), userFromDB.getPassword());
                JsonObject json = Json.createObjectBuilder().add("token", AuthenticationFilter.AUTHENTICATION_SCHEME + " " + token).build();
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving user").build();
        }

    }

    @GET
    @Path("/myprofile")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "PHYSICIAN"})
    public Response test(@HeaderParam("Authorization") String authorizationHeader){
        String username = Helpers.getAuthenticationUsername(authorizationHeader);
        UserDAO dao = new UserDAO();
        try {
            User user = dao.getUserByUsername(username);
            if (user != null) {
                user.setPassword(null);
                return Response.ok(user, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving user").build();
        }
    }


}
