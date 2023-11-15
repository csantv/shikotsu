package pe.tcloud.shikotsu.medical.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.medical.model.PatientPreliminaryHistory;
import pe.tcloud.shikotsu.medical.repository.PatientPreliminaryHistoryRepository;
import pe.tcloud.shikotsu.medicalhr.repository.DoctorRepository;


@RestController
@RequestMapping("/api/v1/preliminary")
public class PatientPreliminaryHistoryWriteController {
    private final PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository;
    private final DoctorRepository doctorRepository;

    public PatientPreliminaryHistoryWriteController(PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository,
                                                    DoctorRepository doctorRepository) {
        this.patientPreliminaryHistoryRepository = patientPreliminaryHistoryRepository;
        this.doctorRepository = doctorRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<PatientPreliminaryHistory> newPreliminaryHistory(@AuthenticationPrincipal CustomUser currentUser) {
        var doctor = doctorRepository.findByPersonUserAccountUsernameAndCompany(currentUser.getUsername(), currentUser.getCompany()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
        var preliminary = new PatientPreliminaryHistory();
        preliminary.setDoctor(doctor);
        preliminary.setCompany(doctor.getCompany());
        preliminary = patientPreliminaryHistoryRepository.save(preliminary);
        return ResponseEntity.ok(preliminary);
    }
}
