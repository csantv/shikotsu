package pe.tcloud.shikotsu.medicalhr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.medicalhr.dto.PatientDTO;
import pe.tcloud.shikotsu.medicalhr.model.Patient;
import pe.tcloud.shikotsu.medicalhr.repository.PatientRepository;

import java.util.ArrayList;
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
    public ResponseEntity<List<PatientDTO>> queryAllFromCompany(@AuthenticationPrincipal CustomUser currentUser) {
        var result = new ArrayList<PatientDTO>();
        var patients = patientRepository.findAllByCompanyId(currentUser.getCompany().getCompanyId());
        patients.forEach(patient -> {
            var entry = new PatientDTO();
            ReflectionUtils.shallowCopyFieldState(patient.getPerson(), entry);
            entry.setPatientId(patient.getPatientId());
            result.add(entry);
        });
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> queryByPersonId(@PathVariable UUID patientId) {
        var patient = patientRepository.findById(patientId);
        return ResponseEntity.of(patient);
    }
}
