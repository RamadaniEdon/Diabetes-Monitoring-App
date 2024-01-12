package city.org.rs;

import java.net.URI;
import java.net.URISyntaxException;
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

    private UserDAO dao = UserDAO.getInstance();

    // API to list all users
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUsers() {
        return dao.getAllUsers();
    }

    // API to insert new user
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) throws URISyntaxException {
        dao.addUser(user);
        URI uri = new URI("/users/" + user.getUserId());
        return Response.created(uri).build();
    }

    // API to update existing user
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        user.setUserId(id);
        if (dao.updateUser(user)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    // API to delete user
    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int id) {
        if (dao.deleteUser(id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    // API to get a single user
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        User user = dao.getUser(id);
        if (user != null) {
            return Response.ok(user, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
