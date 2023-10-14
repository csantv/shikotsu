package pe.tcloud.shikotsu.medicalhr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.tcloud.shikotsu.medicalhr.model.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    @Query("""
        select p from Patient p
        where p.company.companyId = :companyId
        and p.isActive = true
""")
    List<Patient> findAllByCompanyId(UUID companyId);

    @Query("""
        select p from Patient p
        where p.person.personId = :personId and
              p.company.companyId = :companyId
        and p.isActive = true
""")
    Optional<Patient> findByPersonIdAndCompanyId(UUID personId, UUID companyId);
}
