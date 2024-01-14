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
import city.org.rs.utils.PasswordUtil;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.util.Date;

/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 */
@Provider
public class AuthenticationFilter implements jakarta.ws.rs.container.ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    public static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        if(method.isAnnotationPresent(PermitAll.class)) {
            return;
        }
        if(method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Access blocked for all users !!").build());
            return;
        }
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            try {
                final MultivaluedMap<String, String> headers = requestContext.getHeaders();
                final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
                final String encodedUserInfo = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
                DecodedJWT decodedJWT = JwtUtil.verifyToken(encodedUserInfo);
                String username = JwtUtil.getDecodedUsername(decodedJWT);
                String password = JwtUtil.getDecodedPassword(decodedJWT);
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
    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        UserDAO dao = new UserDAO();
        try {
            User user = dao.getUserByUsername(username);
            if(!password.equals(user.getPassword())){
                return false;
            }
            if (rolesSet.contains(user.getRole())){
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
