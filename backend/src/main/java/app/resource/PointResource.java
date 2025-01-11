package app.resource;

import app.DTO.PointDTO;
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
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

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
    public Point create(@Valid PointDTO dto, @Context SecurityContext securityContext) {
        User user = authService.getCurrentUser(securityContext);
        return pointCheckService.createPoint(dto, user);
    }

    @GET
    public List<Point> getAll(@Context SecurityContext securityContext) {
        User user = authService.getCurrentUser(securityContext);
        return pointIndexService.getAllByUser(user);
    }
}
