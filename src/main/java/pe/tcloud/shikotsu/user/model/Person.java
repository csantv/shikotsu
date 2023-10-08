package pe.tcloud.shikotsu.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import pe.tcloud.shikotsu.tenant.model.Company;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
public class Person {
    @Id
    private UUID personId;

    private String name;

    private String lastName;

    private String email;

    private Instant birthDate;

    @OneToOne
    @PrimaryKeyJoinColumn
    private UserAccount userAccount;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Company company;
}
