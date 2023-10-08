package pe.tcloud.shikotsu.tenant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Company {
    @Id
    private UUID companyId;

    private String taxId;

    private String name;
}
