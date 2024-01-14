package city.org.rs.resources;

import java.sql.SQLException;
import java.util.List;

import city.org.rs.dao.DailyRecordDAO;
import city.org.rs.dao.PatientDAO;
import city.org.rs.dao.UserDAO;
import city.org.rs.models.DailyRecord;
import city.org.rs.models.Patient;
import city.org.rs.models.User;
import city.org.rs.utils.Averages;
import city.org.rs.utils.Helpers;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/patients")
public class PatientDailyRecordResource {
  private PatientDAO patientDAO = new PatientDAO();
  private UserDAO userDAO = new UserDAO();
  private DailyRecordDAO dailyRecordDAO = new DailyRecordDAO();
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({"ADMIN", "PHYSICIAN"})
  @Path("{id}/dailyrecords")
  public Response getPatientDailyRecords(@PathParam("id") int id,@HeaderParam("startDate") String startDate, @HeaderParam("endDate") String endDate, @HeaderParam("Authorization") String authorizationHeader) {
    String username = Helpers.getAuthenticationUsername(authorizationHeader);
    try {
      User user = userDAO.getUserByUsername(username);
      if(!user.getRole().equals("ADMIN") && !userDAO.isUserPatient(user, id)) {
        return Response.status(Response.Status.FORBIDDEN).entity("Access denied").build();
      }
      List<DailyRecord> records = dailyRecordDAO.getDailyRecordsByPatient(id, startDate, endDate);
      return Response.ok(records, MediaType.APPLICATION_JSON).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving daily records").build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({"ADMIN", "PHYSICIAN"})
  @Path("{id}/dailyrecords/average")
  public Response getPatientDailyRecordAverages(@PathParam("id") int id,@HeaderParam("startDate") String startDate,@HeaderParam("endDate") String endDate, @HeaderParam("Authorization") String authorizationHeader) {
    String username = Helpers.getAuthenticationUsername(authorizationHeader);
    try {
      User user = userDAO.getUserByUsername(username);
      if(!user.getRole().equals("ADMIN") && !userDAO.isUserPatient(user, id)) {
        return Response.status(Response.Status.FORBIDDEN).entity("Access denied").build();
      }
      Averages averages = dailyRecordDAO.getAverages(id, startDate, endDate);
      return Response.ok(averages, MediaType.APPLICATION_JSON).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving daily records").build();
    }
  }

}
