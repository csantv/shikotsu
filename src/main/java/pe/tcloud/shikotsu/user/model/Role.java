package pe.tcloud.shikotsu.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Role {
    @Id
    private UUID roleId;

    private String name;

    @ManyToMany(mappedBy = "roleList")
    private List<UserAccount> userAccountList;
}
