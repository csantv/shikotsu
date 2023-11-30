package pe.tcloud.shikotsu.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.medical.model.PatientPreliminaryHistory;

import java.util.List;
import java.util.UUID;

public interface PatientPreliminaryHistoryRepository extends JpaRepository<PatientPreliminaryHistory, UUID> {
    List<PatientPreliminaryHistory> findAllByPatientPatientId(UUID patientId);
}
