package pe.tcloud.shikotsu.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
}
