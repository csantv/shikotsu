package pe.tcloud.shikotsu.finances.model;

import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.user.model.Person;

import java.math.BigInteger;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID invoiceId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Person client;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private BigInteger total;

    private short status;

    @Type(JsonType.class)
    private JsonNode lines;
}
