package city.org.rs.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import city.org.rs.dao.DailyRecordDAO;
import city.org.rs.dao.UserDAO;
import city.org.rs.models.DailyRecord;
import city.org.rs.models.User;
import city.org.rs.utils.Helpers;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dailyrecords")
public class DailyRecordResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "PHYSICIAN"})
    public Response listDailyRecords(@HeaderParam("Authorization") String authorizationHeader) {
        String username = Helpers.getAuthenticationUsername(authorizationHeader);
        DailyRecordDAO dao = new DailyRecordDAO();
        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.getUserByUsername(username);
            if(user.getRole().equals("ADMIN")) {
                List<DailyRecord> records = dao.getAllDailyRecords();
                return Response.ok(records, MediaType.APPLICATION_JSON).build();
            }
            else {
                List<DailyRecord> records = dao.getDailyRecordsByPhysician(user);
                return Response.ok(records, MediaType.APPLICATION_JSON).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving daily records").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "PHYSICIAN"})
    public Response addDailyRecord(DailyRecord record) {
        DailyRecordDAO dao = new DailyRecordDAO();
        try {
            dao.addDailyRecord(record);
            URI uri = new URI("/dailyrecords/" + record.getRecordId());
            return Response.created(uri).build();
        } catch (SQLException | URISyntaxException e) {
            if(e.getMessage().toString().equals("Record already exists"))
                return Response.status(Response.Status.CONFLICT).entity("Record already exists").build();
            else
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding daily record").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
    public Response updateDailyRecord(@PathParam("id") int id, DailyRecord record) {
        DailyRecordDAO dao = new DailyRecordDAO();
        try {
            record.setRecordId(id);
            dao.updateDailyRecord(record);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating daily record").build();
        }
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
    public Response deleteDailyRecord(@PathParam("id") int id) {
        DailyRecordDAO dao = new DailyRecordDAO();
        try {
            dao.deleteDailyRecord(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting daily record").build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "PHYSICIAN"})
    public Response getDailyRecord(@PathParam("id") int id) {
        DailyRecordDAO dao = new DailyRecordDAO();
        try {
            DailyRecord record = dao.getDailyRecord(id);
            if (record != null) {
                return Response.ok(record, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving daily record").build();
        }
    }
    
}
