package app.service;

import app.DTO.response.ErrorResponse;
import app.model.User;
import app.repository.UserRepository;
import app.utils.JwtUtils;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class LoginService {
    @EJB
    private UserRepository userRepository;

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(new ErrorResponse("No such user."))
                            .build()
            );
        }

        boolean passwordMatches = BCrypt.checkpw(password, user.getPasswordHash());
        if (!passwordMatches) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(new ErrorResponse("Invalid password."))
                            .build()
            );
        }

        return JwtUtils.generateToken(user.getUsername());
    }
}
