package pe.tcloud.shikotsu.medicalhr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.tcloud.shikotsu.medicalhr.model.Doctor;
import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.user.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    Optional<Doctor> findByPersonUserAccount(UserAccount userAccount);

    Optional<Doctor> findByPersonUserAccountUsernameAndCompany(String username, Company company);
}
