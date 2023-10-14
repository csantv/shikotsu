package pe.tcloud.shikotsu.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.user.dto.PersonDTO;

import java.math.BigInteger;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID personId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    private String email;

    private Instant birthDate;

    private BigInteger phoneNumber;

    @Column(nullable = false)
    private BigInteger identificationNumber;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isActive = true;

    @Transient
    @JsonProperty
    private Long age;

    @PostLoad
    public void calculateAge() {
        if (birthDate == null) {
            age = 0L;
            return;
        }
        age = ChronoUnit.YEARS.between(birthDate.atZone(ZoneOffset.UTC), ZonedDateTime.now(ZoneOffset.UTC));
    }

    public Person(PersonDTO dto) {
        this.name = dto.name();
        this.lastName = dto.lastName();
        this.email = dto.email();
        this.birthDate = ZonedDateTime.parse(dto.birthDate()).toInstant();
        this.phoneNumber = BigInteger.valueOf(dto.phoneNumber());
        this.identificationNumber = BigInteger.valueOf(dto.identificationNumber());
    }

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
