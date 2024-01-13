package city.org.rs;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.auth0.jwt.interfaces.DecodedJWT;

import city.org.rs.dao.UserDAO;
import city.org.rs.models.User;
import city.org.rs.utils.JwtUtil;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 */
@Provider
public class AuthenticationFilter implements jakarta.ws.rs.container.ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            try {

                final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
                final String encodedUserInfo = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
                DecodedJWT decodedJWT = JwtUtil.verifyToken(encodedUserInfo);
                String username = decodedJWT.getClaim("username").asString();
                String password = decodedJWT.getClaim("password").asString();
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                if (!isUserAllowed(username, password, rolesSet)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You cannot access this resource").build());
                    return;
                }
            } catch (Exception e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
                return;
            }

        }
        // Method method = resourceInfo.getResourceMethod();
        // //Access allowed for all
        // if( ! method.isAnnotationPresent(PermitAll.class))
        // {
        // //Access denied for all
        // if(method.isAnnotationPresent(DenyAll.class))
        // {
        // requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
        // .entity("Access blocked for all users !!").build());
        // return;
        // }

        // //Get request headers
        // final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        // //Fetch authorization header
        // final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        // //If no authorization information present; block access
        // if(authorization == null || authorization.isEmpty())
        // {
        // requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
        // .entity("You cannot access this resource").build());
        // return;
        // }

        // //Get encoded username and password
        // final String encodedUserPassword =
        // authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        // //Decode username and password
        // // String usernameAndPassword = new
        // String(Base64.decode(encodedUserPassword.getBytes()));;

        // //Split username and password tokens
        // // final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword,
        // ":");
        // final String username = "edon";
        // final String password = "baba";

        // //Verifying Username and password
        // System.out.println(username);
        // System.out.println(password);

        // //Verify user access
        // if(method.isAnnotationPresent(RolesAllowed.class))
        // {
        // RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
        // Set<String> rolesSet = new
        // HashSet<String>(Arrays.asList(rolesAnnotation.value()));

        // //Is user valid?
        // if( ! isUserAllowed(username, password, rolesSet))
        // {
        // requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
        // .entity("You cannot access this resource").build());
        // return;
        // }
        // }
        // }
    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        boolean isAllowed = false;

        UserDAO dao = new UserDAO();
        try {
            User user = dao.getUserByUsernameAndPassword(username, password);
            String userRole = user.getRole();
            if (rolesSet.contains(userRole)) {
                isAllowed = true;
            }
            return isAllowed;
        } catch (SQLException e) {
        }
        return isAllowed;
        // Step 1. Fetch password from database and match with password in argument
        // If both match then get the defined role for user from database and continue;
        // else return isAllowed [false]
        // Access the database and do this part yourself
        // String userRole = userMgr.getUserRole(username);

    }
}
