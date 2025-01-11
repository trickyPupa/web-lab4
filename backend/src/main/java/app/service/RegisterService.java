package app.service;

import app.model.User;
import app.repository.UserRepository;
import app.utils.JwtUtils;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class RegisterService {
    @EJB
    private UserRepository userRepository;

    public String register(String username, String password) {
        User user = new User();
        user.setUsername(username);

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPasswordHash(passwordHash);

        userRepository.save(user);


        return JwtUtils.generateToken(user.getUsername());
    }
}
