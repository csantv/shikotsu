package pe.tcloud.shikotsu.medicalhr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.medicalhr.model.Patient;
import pe.tcloud.shikotsu.medicalhr.repository.PatientRepository;
import pe.tcloud.shikotsu.user.model.Person;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientReadController {
    private final PatientRepository patientRepository;

    public PatientReadController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> queryAllFromCompany(@AuthenticationPrincipal CustomUser currentUser) {
        var patients = patientRepository.findAllByCompanyId(currentUser.getCompany().getCompanyId());
        var persons = patients.stream().map(Patient::getPerson).toList();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> queryByPatientId(@AuthenticationPrincipal CustomUser currentUser,
                                                    @PathVariable UUID patientId) {
        var patient = patientRepository.findByPersonIdAndCompanyId(patientId, currentUser.getCompany().getCompanyId());
        return ResponseEntity.of(patient);
    }
}
