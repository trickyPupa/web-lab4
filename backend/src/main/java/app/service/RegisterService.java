package app.service;

import app.exception.RegistrationException;
import app.model.User;
import app.repository.UserRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
@Log4j2
public class RegisterService {
    @EJB
    private UserRepository userRepository;

    public void register(String username, String password) {
        if(userRepository.findByUsername(username) != null) {
            log.warn("Username is already in use");
            throw new RegistrationException("Username is already in use");
        }

        User user = new User();
        user.setUsername(username);

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPasswordHash(passwordHash);

        userRepository.save(user);
        log.info("User {} registered successfully", username);
    }
}
