package app.filter;

import app.DTO.response.ErrorResponse;
import app.security.JwtSecurityContext;
import app.utils.Auth;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Priority;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.log4j.Log4j2;

@Provider
@Auth
@Priority(Priorities.AUTHORIZATION)
@Log4j2
public class AuthFilter implements ContainerRequestFilter {

    private static final String SECURITY_KEY = System.getenv("SECURITY_KEY");

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Authorization header is missing or invalid");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECURITY_KEY))
                    .build()
                    .verify(token);

            String username = jwt.getSubject();

            requestContext.setSecurityContext(new JwtSecurityContext(username));

            log.info("auth successful, user {}", username);
        } catch (JWTVerificationException e) {
            log.error("auth failed", e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token verification failed."))
                    .build());
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Unknown error occurred during auth."))
                    .build());
        }
    }
}
