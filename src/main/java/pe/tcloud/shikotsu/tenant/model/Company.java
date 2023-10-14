package pe.tcloud.shikotsu.tenant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import pe.tcloud.shikotsu.medicalhr.model.Doctor;
import pe.tcloud.shikotsu.user.model.Person;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue
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
