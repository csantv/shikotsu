package pe.tcloud.shikotsu.medical.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.tcloud.shikotsu.tenant.model.Company;

import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "teeth_status")
public class TeethStatus {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID teethStatusId;

    private String name;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
