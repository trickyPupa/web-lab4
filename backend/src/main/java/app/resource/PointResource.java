package app.resource;

import app.DTO.response.ErrorResponse;
import app.DTO.request.PointRequest;
import app.DTO.response.SuccessResponse;
import app.DTO.response.PointResponse;
import app.model.Point;
import app.model.User;
import app.service.AuthService;
import app.service.PointCheckService;
import app.service.PointIndexService;
import app.utils.Auth;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Auth
@Path("/point")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {
    @EJB
    private PointCheckService pointCheckService;
    @EJB
    private AuthService authService;
    @EJB
    private PointIndexService pointIndexService;

    @POST
    public Response create(@Valid PointRequest dto, @Context SecurityContext securityContext) {
        log.info("point create request: {}", dto.toString());
        User user = authService.getCurrentUser(securityContext);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("User not found"))
                    .build();
        }

        Point point = pointCheckService.createPoint(dto, user);
        return Response.ok()
                .entity(new SuccessResponse(new PointResponse(point)))
                .build();
    }

    @GET
    public Response getAll(@Context SecurityContext securityContext) {
        log.info("point get all request");
        User user = authService.getCurrentUser(securityContext);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("User not found"))
                    .build();
        }

        var result = pointIndexService.getAllByUser(user);
        log.info("point get all result: {}", result.toString());
        return Response.ok()
                .entity(new SuccessResponse())
                .build();
    }
}
