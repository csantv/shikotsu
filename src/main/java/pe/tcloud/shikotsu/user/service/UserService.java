package pe.tcloud.shikotsu.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import pe.tcloud.shikotsu.auth.JwtUtil;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserService(AuthenticationConfiguration authenticationConfiguration,
                       JwtUtil jwtUtil) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtUtil = jwtUtil;
    }

    public String authenticateUser(String username, String password) throws AuthenticationException {
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
        return jwtUtil.generateToken(username);
    }
}
