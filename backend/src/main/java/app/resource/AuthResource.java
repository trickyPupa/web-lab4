package app.resource;

import app.DTO.response.ErrorResponse;
import app.DTO.request.LoginRequest;
import app.DTO.response.SuccessResponse;
import app.service.LoginService;
import app.service.RegisterService;
import app.utils.Auth;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Data
    public static class Token {
        private String token;

        public Token(String token) {
            this.token = token;
        }
    }

    @EJB
    private LoginService loginService;
    @EJB
    private RegisterService registerService;

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest dto) {
        log.info("login request: {}", dto.toString());
        try {
            Token token = new Token(loginService.login(dto.getUsername(), dto.getPassword()));

            return Response.ok()
                    .entity(new SuccessResponse(token))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Unknown error occurred"))
                    .build();
        }
    }

    @POST
    @Path("/register")
    public Response register(@Valid LoginRequest dto) {
        log.info("point register request: {}", dto.toString());
        try {
            Token token = new Token(registerService.register(dto.getUsername(), dto.getPassword()));

            return Response.ok()
                    .entity(new SuccessResponse(token))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Unknown error occurred"))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    @Auth
    public Response logout(@Context SecurityContext securityContext) {
        log.info("point logout request");
        try {
            return Response.ok()
                    .entity(new SuccessResponse("Successfully logged out"))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Unknown error occurred"))
                    .build();
        }
    }
}
