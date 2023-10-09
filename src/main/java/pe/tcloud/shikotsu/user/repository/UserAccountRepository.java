package pe.tcloud.shikotsu.user.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    @EntityGraph(attributePaths = {"roleList", "company"})
    Optional<UserAccount> findByUsernameAndCompanyTaxId(String username, String companyTaxId);
}
