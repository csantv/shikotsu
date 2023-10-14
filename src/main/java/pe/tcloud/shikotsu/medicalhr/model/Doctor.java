package pe.tcloud.shikotsu.medicalhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.user.model.Person;

import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
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
