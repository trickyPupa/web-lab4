package app.resource;

import app.DTO.LoginDTO;
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

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @EJB
    private LoginService loginService;

    @EJB
    private RegisterService registerService;

    @POST
    @Path("/login")
    public Response login(@Valid LoginDTO dto) {
        try {
            String token = loginService.login(dto.getUsername(), dto.getPassword());

            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred.")
                    .build();
        }
    }

    @POST
    @Path("/register")
    public Response register(@Valid LoginDTO dto) {
        try {
            String token = registerService.register(dto.getUsername(), dto.getPassword());

            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred.")
                    .build();
        }
    }

    @POST
    @Path("/logout")
    @Auth
    public Response logout(@Context SecurityContext securityContext) {
        try {
            return Response.ok()
                    .entity("Successfully logged out")
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred.")
                    .build();
        }
    }
}
