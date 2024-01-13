package city.org.rs.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import city.org.rs.dao.PatientDAO;
import city.org.rs.models.Patient;
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

@Path("/patients")
public class PatientResource {

    // API to list all patients
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPatients() {
        PatientDAO dao = new PatientDAO();
        try {
            List<Patient> patients = dao.getAllPatients();
            return Response.ok(patients, MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving patients").build();
        }
    }

    // API to insert new patient
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        PatientDAO dao = new PatientDAO();
        try {
            dao.addPatient(patient);
            URI uri = new URI("/patients/" + patient.getPatientId());
            return Response.created(uri).build();
        } catch (SQLException | URISyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding patient").build();
        }
    }

    // API to update existing patient
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updatePatient(@PathParam("id") int id, Patient patient) {
        PatientDAO dao = new PatientDAO();
        try {
            patient.setPatientId(id);
            dao.updatePatient(patient);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating patient").build();
        }
    }

    // API to delete patient
    @DELETE
    @Path("{id}")
    public Response deletePatient(@PathParam("id") int id) {
        PatientDAO dao = new PatientDAO();
        try {
            dao.deletePatient(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting patient").build();
        }
    }

    // API to get a single patient
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatient(@PathParam("id") int id) {
        PatientDAO dao = new PatientDAO();
        try {
            Patient patient = dao.getPatient(id);
            if (patient != null) {
                return Response.ok(patient, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving patient").build();
        }
    }
}
