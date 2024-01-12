package city.org.rs;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    // API to list all users
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        UserDAO dao = new UserDAO();
        try {
            List<User> users = dao.getAllUsers();
            return Response.ok(users, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving users").build();
        }
    }

    // API to insert new user
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
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

    // API to update existing user
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
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

    // API to delete user
    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int id) {
        UserDAO dao = new UserDAO();
        try {
            dao.deleteUser(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting user").build();
        }
    }

    // API to get a single user
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
}
