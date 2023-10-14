package pe.tcloud.shikotsu.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import pe.tcloud.shikotsu.tenant.model.Company;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue
    private UUID personId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    private String email;

    private Instant birthDate;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isActive = true;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
