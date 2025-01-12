package app.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/")
public class TestResource {
    @GET
    public Response index(){
        return Response.ok("Hello World!").build();
    }
}
