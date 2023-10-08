package pe.tcloud.shikotsu.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.user.dto.LoginInformationDTO;
import pe.tcloud.shikotsu.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> loginHandler(@RequestBody LoginInformationDTO login) {
        String jwtToken;
        try {
            jwtToken = userService.authenticateUser(login.getUsername(), login.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(jwtToken);
    }
}
