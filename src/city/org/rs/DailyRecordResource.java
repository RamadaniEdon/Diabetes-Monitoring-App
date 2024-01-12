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

@Path("/dailyrecords")
public class DailyRecordResource {

    private DailyRecordDAO dao = DailyRecordDAO.getInstance();

    // API to list all daily records
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DailyRecord> listDailyRecords() {
        return dao.getAllDailyRecords();
    }

    // API to insert new daily record
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDailyRecord(DailyRecord record) throws URISyntaxException {
        dao.addDailyRecord(record);
        URI uri = new URI("/dailyrecords/" + record.getRecordId());
        return Response.created(uri).build();
    }

    // API to update existing daily record
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateDailyRecord(@PathParam("id") int id, DailyRecord record) {
        record.setRecordId(id);
        if (dao.updateDailyRecord(record)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    // API to delete daily record
    @DELETE
    @Path("{id}")
    public Response deleteDailyRecord(@PathParam("id") int id) {
        if (dao.deleteDailyRecord(id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    // API to get a single daily record
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDailyRecord(@PathParam("id") int id) {
        DailyRecord record = dao.getDailyRecord(id);
        if (record != null) {
            return Response.ok(record, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
