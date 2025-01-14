package app.service;

import app.model.User;
import app.repository.UserRepository;
import app.utils.JwtUtils;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
@Log4j2
public class RegisterService {
    @EJB
    private UserRepository userRepository;

    public String register(String username, String password) {
        User user = new User();
        user.setUsername(username);

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPasswordHash(passwordHash);

        userRepository.save(user);
        log.info("User {} registered successfully", username);


        return JwtUtils.generateToken(user.getUsername());
    }
}
