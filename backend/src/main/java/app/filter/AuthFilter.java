package app.filter;

import app.DTO.ErrorResponse;
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

import java.io.IOException;

@Provider
@Auth
@Priority(Priorities.AUTHORIZATION)
@Log4j2
public class AuthFilter implements ContainerRequestFilter {

    private static final String SECURITY_KEY = System.getenv("SECURITY_KEY");

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.info("Request received " + requestContext.getRequest());

        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.substring("Bearer ".length());
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECURITY_KEY))
                    .build()
                    .verify(token);

            String username = jwt.getSubject();

            requestContext.setSecurityContext(new JwtSecurityContext(username));
        } catch (JWTVerificationException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Unknown error occurred during auth."))
                    .build());
        }
    }
}
