package pe.tcloud.shikotsu.medical.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.medical.model.PatientPreliminaryHistory;
import pe.tcloud.shikotsu.medical.repository.PatientPreliminaryHistoryRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/preliminary")
public class PatientPreliminaryHistoryReadController {
    private final PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository;

    public PatientPreliminaryHistoryReadController(PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository) {
        this.patientPreliminaryHistoryRepository = patientPreliminaryHistoryRepository;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PatientPreliminaryHistory> getPreliminaryHistory(@PathVariable UUID uuid) {
        var preliminary = patientPreliminaryHistoryRepository.findById(uuid);
        return ResponseEntity.of(preliminary);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientPreliminaryHistory>> getAllPreliminaries() {
        var result = patientPreliminaryHistoryRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/allByPatient/{patientId}")
    public ResponseEntity<List<PatientPreliminaryHistory>> getAllByPatient(@PathVariable UUID patientId) {
        var result = patientPreliminaryHistoryRepository.findAllByPatientPatientId(patientId);
        return ResponseEntity.ok(result);
    }
}
