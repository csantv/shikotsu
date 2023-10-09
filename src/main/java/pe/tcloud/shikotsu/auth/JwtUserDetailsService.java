package pe.tcloud.shikotsu.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.tcloud.shikotsu.user.repository.UserAccountRepository;

import java.util.HashSet;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public JwtUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var components = username.split(";");
        var userStr = components[0];
        var companyStr = components[1];
        var user = userAccountRepository.findByUsernameAndCompanyTaxId(userStr, companyStr).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));
        var authorities = new HashSet<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        for (var role: user.getRoleList()) {
            authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%S", role.getName())));
        }
        return new CustomUser(companyStr, user.getUsername(), user.getPassword(), user.isActive(),
                true, true, true, authorities);
    }
}
