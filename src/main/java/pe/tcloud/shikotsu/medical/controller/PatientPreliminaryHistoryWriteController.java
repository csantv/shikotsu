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
import pe.tcloud.shikotsu.medicalhr.repository.PatientRepository;


@RestController
@RequestMapping("/api/v1/preliminary")
public class PatientPreliminaryHistoryWriteController {
    private final PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository;
    private final DoctorRepository doctorRepository;
    private final DentalChartRepository dentalChartRepository;
    private final PatientRepository patientRepository;

    public PatientPreliminaryHistoryWriteController(PatientPreliminaryHistoryRepository patientPreliminaryHistoryRepository,
                                                    DoctorRepository doctorRepository,
                                                    DentalChartRepository dentalChartRepository,
                                                    PatientRepository patientRepository) {
        this.patientPreliminaryHistoryRepository = patientPreliminaryHistoryRepository;
        this.doctorRepository = doctorRepository;
        this.dentalChartRepository = dentalChartRepository;
        this.patientRepository = patientRepository;
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
        var patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        var dentalChart = dentalChartRepository.findById(dto.getDentalChartId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        var preliminary = patientPreliminaryHistoryRepository.findById(dto.getPreliminaryHistoryId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        preliminary.setPatient(patient);
        patientPreliminaryHistoryRepository.save(preliminary);
        dentalChart.setPatient(patient);
        dentalChart.setData(dto.getDentalChartData());
        dentalChartRepository.save(dentalChart);
        return ResponseEntity.ok(true);
    }
}
