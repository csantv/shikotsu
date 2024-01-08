package pe.tcloud.shikotsu.finances.model;

import jakarta.persistence.*;
import lombok.*;
import pe.tcloud.shikotsu.medical.model.TeethStatus;
import pe.tcloud.shikotsu.tenant.model.Company;

import java.math.BigInteger;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID serviceId;

    private BigInteger price;

    private String name;

    private BigInteger maxDiscount;

    @ManyToOne
    @JoinColumn(name = "teeth_status_id")
    private TeethStatus teethStatus;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
