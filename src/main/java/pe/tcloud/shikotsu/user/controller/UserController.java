package pe.tcloud.shikotsu.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.medicalhr.repository.DoctorRepository;
import pe.tcloud.shikotsu.user.dto.LoggedInUserDTO;
import pe.tcloud.shikotsu.user.dto.LoginInformationDTO;
import pe.tcloud.shikotsu.user.repository.UserAccountRepository;
import pe.tcloud.shikotsu.user.service.UserService;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserAccountRepository userAccountRepository;
    private final DoctorRepository doctorRepository;

    public UserController(UserService userService,
                          UserAccountRepository userAccountRepository,
                          DoctorRepository doctorRepository) {
        this.userService = userService;
        this.userAccountRepository = userAccountRepository;
        this.doctorRepository = doctorRepository;
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
    public ResponseEntity<LoggedInUserDTO> getLoggedInUser() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken token) {
            var currentUser = (CustomUser) token.getPrincipal();
            var user = userAccountRepository.findByUsernameAndCompanyTaxId(
                    currentUser.getUsername(), currentUser.getCompany().getTaxId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            if (Objects.equals(user.getRole().getName(), "DOCTOR")) {
                var doctor = doctorRepository.findByPersonUserAccount(user).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
                var response = new LoggedInUserDTO(user, doctor.getPerson(), user.getCompany(), doctor.getDoctorId());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
