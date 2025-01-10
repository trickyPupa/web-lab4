package app.resource;

import app.DTO.PointDTO;
import app.model.Point;
import app.model.User;
import app.service.PointCheckService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public class PointResource {
    @Inject
    private PointCheckService pointCheckService;

    @POST
    @Path("/point")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Point checkPoint(@Valid PointDTO dto) {
        User user = null;
        return pointCheckService.check(dto, user);
    }
}
