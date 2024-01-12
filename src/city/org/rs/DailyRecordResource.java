package city.org.rs;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dailyrecords")
public class DailyRecordResource {

    // API to list all daily records
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDailyRecords() {
        DailyRecordDAO dao = new DailyRecordDAO();
        try {
            List<DailyRecord> records = dao.getAllDailyRecords();
            return Response.ok(records, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving daily records").build();
        }
    }

    // API to insert new daily record
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDailyRecord(DailyRecord record) {
        DailyRecordDAO dao = new DailyRecordDAO();
        try {
            dao.addDailyRecord(record);
            URI uri = new URI("/dailyrecords/" + record.getRecordId());
            return Response.created(uri).build();
        } catch (SQLException | URISyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding daily record").build();
        }
    }

    // API to update existing daily record
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
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

    // API to delete daily record
    @DELETE
    @Path("{id}")
    public Response deleteDailyRecord(@PathParam("id") int id) {
        DailyRecordDAO dao = new DailyRecordDAO();
        try {
            dao.deleteDailyRecord(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting daily record").build();
        }
    }

    // API to get a single daily record
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
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

    //  @GET
    // @Path("/period")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response listDailyRecordsInPeriod(@QueryParam("startDate") Date startDate,
    //                                          @QueryParam("endDate") Date endDate) {
    //     DailyRecordDAO dao = new DailyRecordDAO();
    //     try {
    //         List<DailyRecord> records = dao.getDailyRecordsInPeriod(startDate, endDate);
    //         return Response.ok(records, MediaType.APPLICATION_JSON).build();
    //     } catch (SQLException e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving daily records").build();
    //     }
    // }

    // // API to get the average daily blood glucose level over a specified period
    // @GET
    // @Path("/average/bloodglucose")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getAverageGlucose(@QueryParam("startDate") Date startDate,
    //                                   @QueryParam("endDate") Date endDate) {
    //     DailyRecordDAO dao = new DailyRecordDAO();
    //     try {
    //         Double averageGlucose = dao.getAverageBloodGlucoseLevel(startDate, endDate);
    //         return Response.ok(averageGlucose).build();
    //     } catch (SQLException e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error calculating average glucose level").build();
    //     }
    // }

    // // API to get the average carb intake over a specified period
    // @GET
    // @Path("/average/carb")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getAverageCarbIntake(@QueryParam("startDate") Date startDate,
    //                                      @QueryParam("endDate") Date endDate) {
    //     DailyRecordDAO dao = new DailyRecordDAO();
    //     try {
    //         Double averageCarbIntake = dao.getAverageCarbIntake(startDate, endDate);
    //         return Response.ok(averageCarbIntake).build();
    //     } catch (SQLException e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error calculating average carb intake").build();
    //     }
    // }
    
}
