package app.service;

import app.model.User;
import app.repository.UserRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Stateless
public class AuthService {

    @EJB
    private UserRepository userRepository;

    public User getCurrentUser(SecurityContext securityContext) {
        User user = userRepository.findByUsername(securityContext.getUserPrincipal().getName());
        if (user == null) throw new WebApplicationException(Response.Status.FORBIDDEN);
        return user;
    }
}
