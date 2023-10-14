package pe.tcloud.shikotsu.tenant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pe.tcloud.shikotsu.medicalhr.model.Doctor;
import pe.tcloud.shikotsu.user.model.Person;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID companyId;

    @Column(nullable = false, unique = true)
    private String taxId;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<UserAccount> userAccountList;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Person> personList;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Doctor> doctorList;
}
