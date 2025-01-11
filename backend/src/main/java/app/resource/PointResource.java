package app.resource;

import app.DTO.PointDTO;
import app.model.Point;
import app.model.User;
import app.repository.PointRepository;
import app.repository.UserRepository;
import app.service.PointCheckService;
import app.utils.Auth;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Auth
@Path("/point")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {
    @Inject
    private PointCheckService pointCheckService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PointRepository pointRepository;

    @POST
    public Point create(@Valid PointDTO dto, @Context SecurityContext securityContext) {
        User user = getUser(securityContext);
        return pointCheckService.createPoint(dto, user);
    }

    @GET
    public List<Point> getAll(@Context SecurityContext securityContext) {
        User user = getUser(securityContext);
        return pointRepository.getPoints(user);
    }

    private User getUser(SecurityContext securityContext) {
        User user = userRepository.findByUsername(securityContext.getUserPrincipal().getName());
        if (user == null) throw new WebApplicationException(Response.Status.FORBIDDEN);
        return user;
    }
}
