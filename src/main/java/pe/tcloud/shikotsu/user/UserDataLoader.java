package pe.tcloud.shikotsu.user;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.tenant.repository.CompanyRepository;
import pe.tcloud.shikotsu.user.model.UserAccount;
import pe.tcloud.shikotsu.user.repository.UserAccountRepository;

@Component
public class UserDataLoader implements ApplicationRunner {
    private final UserAccountRepository userAccountRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private Company company;

    public UserDataLoader(UserAccountRepository userAccountRepository,
                          PasswordEncoder passwordEncoder,
                          CompanyRepository companyRepository) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        createCompany();
        createUser();
    }

    private void createUser() {
        if (userAccountRepository.count() != 0) {
            return;
        }
        var newUser = new UserAccount();
        newUser.setUsername("jvaler");
        newUser.setPassword(passwordEncoder.encode("12345"));
        newUser.setCompany(company);
        userAccountRepository.save(newUser);
    }

    private void createCompany() {
        if (companyRepository.count() != 0) {
            return;
        }
        var newCompany = new Company();
        newCompany.setName("Dental Expert");
        newCompany.setTaxId("00000000000");
        company = companyRepository.save(newCompany);
    }
}
