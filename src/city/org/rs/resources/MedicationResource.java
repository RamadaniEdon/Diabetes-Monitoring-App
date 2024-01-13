package city.org.rs.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import city.org.rs.dao.MedicationDAO;
import city.org.rs.models.Medication;
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

@Path("/medications")
public class MedicationResource {

    // API to list all medications
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listMedications() {
        MedicationDAO dao = new MedicationDAO();
        try {
            List<Medication> medications = dao.getAllMedications();
            return Response.ok(medications, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving medications").build();
        }
    }

    // API to insert new medication
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedication(Medication medication) {
        MedicationDAO dao = new MedicationDAO();
        try {
            dao.addMedication(medication);
            URI uri = new URI("/medications/" + medication.getMedicationId());
            return Response.created(uri).build();
        } catch (SQLException | URISyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding medication").build();
        }
    }

    // API to update existing medication
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateMedication(@PathParam("id") int id, Medication medication) {
        MedicationDAO dao = new MedicationDAO();
        try {
            medication.setMedicationId(id);
            dao.updateMedication(medication);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating medication").build();
        }
    }

    // API to delete medication
    @DELETE
    @Path("{id}")
    public Response deleteMedication(@PathParam("id") int id) {
        MedicationDAO dao = new MedicationDAO();
        try {
            dao.deleteMedication(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting medication").build();
        }
    }

    // API to get a single medication
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedication(@PathParam("id") int id) {
        MedicationDAO dao = new MedicationDAO();
        try {
            Medication medication = dao.getMedication(id);
            if (medication != null) {
                return Response.ok(medication, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving medication").build();
        }
    }
}
