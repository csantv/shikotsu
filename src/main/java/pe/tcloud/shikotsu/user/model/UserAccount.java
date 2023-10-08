package pe.tcloud.shikotsu.user.model;

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

    private String username;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roleList;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(mappedBy = "userAccount")
    private Person person;
}
