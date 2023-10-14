package pe.tcloud.shikotsu.medicalhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.user.model.Person;

import java.util.UUID;

@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue
    private UUID doctorId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
