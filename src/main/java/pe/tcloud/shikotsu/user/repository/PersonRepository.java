package pe.tcloud.shikotsu.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.user.model.Person;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByUserAccountAndCompanyTaxId(UserAccount userAccount, String company);
}
