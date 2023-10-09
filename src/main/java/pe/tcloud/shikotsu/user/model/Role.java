package pe.tcloud.shikotsu.user.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="name")
@JsonIdentityReference(alwaysAsId=true)
public class Role {
    @Id
    private UUID roleId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roleList")
    private List<UserAccount> userAccountList;
}
