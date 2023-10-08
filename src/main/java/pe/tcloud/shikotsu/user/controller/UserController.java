package pe.tcloud.shikotsu.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.tcloud.shikotsu.user.dto.LoginInformationDTO;
import pe.tcloud.shikotsu.user.model.UserAccount;
import pe.tcloud.shikotsu.user.repository.UserAccountRepository;
import pe.tcloud.shikotsu.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserAccountRepository userAccountRepository;

    public UserController(UserService userService,
                          UserAccountRepository userAccountRepository) {
        this.userService = userService;
        this.userAccountRepository = userAccountRepository;
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

    @GetMapping("/auth/info")
    public ResponseEntity<UserAccount> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken token) {
            var username = token.getPrincipal().toString();
            var userDb = userAccountRepository.findByUsername(username);
            return ResponseEntity.of(userDb);
        }
        return ResponseEntity.badRequest().build();
    }
}
