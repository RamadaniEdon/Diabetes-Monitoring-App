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

@Path("/patients")
public class PatientResource {

    private PatientDAO dao = PatientDAO.getInstance();

    // API to list all patients
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> listPatients() {
        return dao.getAllPatients();
    }

    // API to insert new patient
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) throws URISyntaxException {
        dao.addPatient(patient);
        URI uri = new URI("/patients/" + patient.getPatientId());
        return Response.created(uri).build();
    }

    // API to update existing patient
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updatePatient(@PathParam("id") int id, Patient patient) {
        patient.setPatientId(id);
        if (dao.updatePatient(patient)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    // API to delete patient
    @DELETE
    @Path("{id}")
    public Response deletePatient(@PathParam("id") int id) {
        if (dao.deletePatient(id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    // API to get a single patient
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatient(@PathParam("id") int id) {
        Patient patient = dao.getPatient(id);
        if (patient != null) {
            return Response.ok(patient, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
