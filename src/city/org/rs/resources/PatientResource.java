package city.org.rs.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import city.org.rs.dao.PatientDAO;
import city.org.rs.dao.UserDAO;
import city.org.rs.models.Patient;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/patients")
public class PatientResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "PHYSICIAN"})
    public Response listPatients(@HeaderParam("Authorization") String authorizationHeader) {
        String username = Helpers.getAuthenticationUsername(authorizationHeader);
        UserDAO userDao = new UserDAO();
        PatientDAO dao = new PatientDAO();
        try {
            User user = userDao.getUserByUsername(username);
            if (user.getRole().equals("ADMIN")) {
                List<Patient> patients = dao.getAllPatients();
                return Response.ok(patients, MediaType.APPLICATION_JSON).build();
            }
            else {
                List<Patient> patients = dao.getPatientsByPhysician(user);
                return Response.ok(patients, MediaType.APPLICATION_JSON).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving patients").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
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

    @DELETE
    @Path("{id}")
    @RolesAllowed({"ADMIN"})
    public Response deletePatient(@PathParam("id") int id) {
        PatientDAO dao = new PatientDAO();
        try {
            dao.deletePatient(id);
            return Response.ok().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting patient").build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "PHYSICIAN"})
    public Response getPatient(@PathParam("id") int id, @HeaderParam("Authorization") String authorizationHeader) {
        String username = Helpers.getAuthenticationUsername(authorizationHeader);
        UserDAO userDao = new UserDAO();
        PatientDAO dao = new PatientDAO();
        try {
            User user = userDao.getUserByUsername(username);
            Patient patient = dao.getPatient(id);
            if (patient != null && (user.getRole().equals("ADMIN") || patient.getUserId() == user.getUserId())) {
                return Response.ok(patient, MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving patient").build();
        }
    }
}
