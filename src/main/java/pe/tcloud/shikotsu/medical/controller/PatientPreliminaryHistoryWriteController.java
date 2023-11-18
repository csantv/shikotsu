package pe.tcloud.shikotsu.medical.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.medical.dto.PreliminaryHistoryDTO;
import pe.tcloud.shikotsu.medical.model.DentalChart;
import pe.tcloud.shikotsu.medical.model.PatientPreliminaryHistory;
import pe.tcloud.shikotsu.medical.repository.DentalChartRepository;
import pe.tcloud.shikotsu.medical.repository.PatientPreliminaryHistoryRepository;
import pe.tcloud.shikotsu.medicalhr.repository.DoctorRepository;


@RestController
@RequestMapping("/api/v1/preliminary")
public class PatientPreliminaryHistoryWriteController {
    private final PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository;
    private final DoctorRepository doctorRepository;
    private final DentalChartRepository dentalChartRepository;

    public PatientPreliminaryHistoryWriteController(PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository,
                                                    DoctorRepository doctorRepository,
                                                    DentalChartRepository dentalChartRepository) {
        this.patientPreliminaryHistoryRepository = patientPreliminaryHistoryRepository;
        this.doctorRepository = doctorRepository;
        this.dentalChartRepository = dentalChartRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<PatientPreliminaryHistory> newPreliminaryHistory(@AuthenticationPrincipal CustomUser currentUser) {
        var doctor = doctorRepository.findByPersonUserAccountUsernameAndCompany(currentUser.getUsername(), currentUser.getCompany()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        var dentalChart = new DentalChart();
        dentalChart.setCompany(doctor.getCompany());
        dentalChart = dentalChartRepository.save(dentalChart);

        var preliminary = new PatientPreliminaryHistory();
        preliminary.setDoctor(doctor);
        preliminary.setCompany(doctor.getCompany());
        preliminary.setDentalChart(dentalChart);
        preliminary = patientPreliminaryHistoryRepository.save(preliminary);
        return ResponseEntity.ok(preliminary);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updatePreliminaryHistory(@RequestBody PreliminaryHistoryDTO dto) {
        return ResponseEntity.ok(true);
    }
}
