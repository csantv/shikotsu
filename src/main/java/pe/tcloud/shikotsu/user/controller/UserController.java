package pe.tcloud.shikotsu.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.tenant.repository.CompanyRepository;
import pe.tcloud.shikotsu.user.dto.LoginInformationDTO;
import pe.tcloud.shikotsu.user.model.UserAccount;
import pe.tcloud.shikotsu.user.repository.PersonRepository;
import pe.tcloud.shikotsu.user.repository.UserAccountRepository;
import pe.tcloud.shikotsu.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserAccountRepository userAccountRepository;
    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;

    public UserController(UserService userService,
                          UserAccountRepository userAccountRepository,
                          PersonRepository personRepository,
                          CompanyRepository companyRepository) {
        this.userService = userService;
        this.userAccountRepository = userAccountRepository;
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> loginHandler(@RequestBody LoginInformationDTO login) {
        String jwtToken;
        try {
            jwtToken = userService.authenticateUser(login.getUsername(), login.getCompany(), login.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/auth/info")
    public ResponseEntity<UserAccount> getLoggedInUser() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken token) {
            var currentUser = (CustomUser) token.getPrincipal();
            var userDb = userAccountRepository.findByUsernameAndCompany(currentUser.getUsername(), currentUser.getCompany());
            userDb.ifPresent(userAccount -> userAccount.setCompany(currentUser.getCompany()));
            return ResponseEntity.of(userDb);
        }
        return ResponseEntity.badRequest().build();
    }
}
