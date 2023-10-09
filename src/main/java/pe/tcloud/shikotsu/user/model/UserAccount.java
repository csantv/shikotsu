package pe.tcloud.shikotsu.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import pe.tcloud.shikotsu.tenant.model.Company;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue
    private UUID userAccountId;

    @Column(nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_account_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
    )
    private List<Role> roleList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
