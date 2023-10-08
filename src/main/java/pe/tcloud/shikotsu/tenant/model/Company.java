package pe.tcloud.shikotsu.tenant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue
    private UUID companyId;

    private String taxId;

    private String name;

    @OneToMany(mappedBy = "company")
    private List<UserAccount> userAccountList;
}
