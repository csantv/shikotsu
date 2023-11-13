package pe.tcloud.shikotsu.medicalhr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.tcloud.shikotsu.user.model.Person;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO extends Person {
    private UUID patientId;
}
