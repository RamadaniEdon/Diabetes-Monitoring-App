
package city.org.rs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/test")
public class Test {
	
	//DAO class creates and returns its single instance
	//This instance is then used by all APIs below to call the appropriate DAO methods
	// private ProductDAO dao = ProductDAO.getInstance();
	 public String kodi() {
        // Your encoded JWT token
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImVkb24iLCJwYXNzd29yZCI6ImJhYmEifQ.BMhThKx0T8a6OFiavEwwF1eXcf5zdanMrbz8CM5DtuY";

        try {
            // Decode the JWT token
            DecodedJWT decodedJWT = decodeJwt(jwtToken);

            // Access values from the decoded JWT
            String username = decodedJWT.getClaim("username").asString();
            String password = decodedJWT.getClaim("password").asString();

            // Print the values
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            return username + " " + password;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static DecodedJWT decodeJwt(String jwtToken) {
        // Your secret key for verifying the token
        String secretKey = "11111111";

        // Decode the JWT token
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(jwtToken);
    }


	//API to list all products
	@GET
	@Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("ADMIN")
	public String list() {
		return kodi();
	}
  @GET
	@Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({"PHYSICIAN"})
  @Path("/1")
	public String list1() {
		return kodi();
	}
  @GET
	@Produces(MediaType.APPLICATION_JSON)
  @Path("/2")
	public String list2() {
		return kodi();
	}
	
	//API to insert new product
	/* NOTE:
	 * ----
	 * When the below method is invoked by a request it returns a response inside a Response object.
	 * Response is a class that CANNOT b instantiated directly (e.g. through the "new" command).
	 * Instead, it uses the so called "builder design pattern" whereby object creation is delegated 
	 * to a set of builders (hence the .build() method call below). This is because responses are complex 
	 * entities: a response comprises 3 lines (status line, headers, body) and, if headers are present, they may 
	 * encompass many (optional) key/value pairs. As such, responses need to be represented through complex 
	 * objects. 
	 * 
	 * These complex objects can be constructed through overloaded constructors but the whole thing
	 * gets really messy when one considers optional parameters (each requires a separate constructor). 
	 * Alternatively, one could implement a single no-args constructor and then rely on a multitude 
	 * of setter methods for setting all the different header parameters and options but this too gets 
	 * messy. Instead with the builder design pattern a separate builder is used for creating each
	 * different part of a response message. For instance, in the POST API below, a builder is used
	 * for building (instantiating) a response that returns the created URI, whereas in the PUT API below, a
	 * different builder is invoked for creating a different response that reports a status code.  
	 * 
	 * 
	 * Same comment applies to the PUT, DELETE and GET APIs below.
	 */
	

	
	
	


	
}
