package pe.tcloud.shikotsu.medicalhr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.auth.CustomUser;
import pe.tcloud.shikotsu.medicalhr.model.Patient;
import pe.tcloud.shikotsu.medicalhr.repository.PatientRepository;
import pe.tcloud.shikotsu.user.dto.PersonDTO;
import pe.tcloud.shikotsu.user.model.Person;
import pe.tcloud.shikotsu.user.repository.PersonRepository;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientWriteController {
    private final PersonRepository personRepository;
    private final PatientRepository patientRepository;

    public PatientWriteController(PersonRepository personRepository,
                                  PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        this.personRepository = personRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<Patient> newPatient(@AuthenticationPrincipal CustomUser currentUser,
                                             @RequestBody PersonDTO dto) {
        var newPerson = new Person(dto);
        newPerson.setCompany(currentUser.getCompany());
        newPerson = personRepository.save(newPerson);

        var patient = new Patient();
        patient.setPerson(newPerson);
        patient.setCompany(currentUser.getCompany());
        patient = patientRepository.save(patient);
        return ResponseEntity.ok(patient);
    }
}
