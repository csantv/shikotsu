package pe.tcloud.shikotsu.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pe.tcloud.shikotsu.tenant.model.Company;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    private Company company;

    public CustomUser(Company company, String username, String password,
                      boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired, boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.company = company;
    }
}
