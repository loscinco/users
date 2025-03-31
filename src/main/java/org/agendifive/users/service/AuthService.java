package org.agendifive.users.service;


import org.agendifive.users.model.Response;
import org.agendifive.users.util.JwtUtil;
import org.agendifive.users.model.Session;
import org.agendifive.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService implements AuthInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    /*@PostConstruct
    public void generatePassword() {
        String rawPassword = "admin123";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(rawPassword);

        System.out.println("Contraseña sin encriptar: " + rawPassword);
        System.out.println("Contraseña encriptada: " + hashedPassword);
    }*/
    @Override
    public Response authenticate(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Response response = new Response();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword()) && "A".equals(user.getStatus())) {
                String token = jwtUtil.generateToken(user.getUsername());

                // Guardar sesión en la base de datos
                Session session = new Session();
                session.setUser(user);
                session.setToken(token);
                session.setStatus("A");
                session.setCreatedAt(LocalDateTime.now());
                session.setExpiresAt(LocalDateTime.now().plusDays(1)); // Expira en 1 día

                sessionRepository.save(session);
                response.setToken(token);
                response.setMessage("usuario y contraseña correctas");
                response.setSuccess(true);
            }else{
                response.setMessage("Usuario o contraseña Invalidos");
                response.setSuccess(false);
            }
        }else{
            response.setMessage("Usuario o contraseña Invalidos");
            response.setSuccess(false);
        }
        return response;
    }
}