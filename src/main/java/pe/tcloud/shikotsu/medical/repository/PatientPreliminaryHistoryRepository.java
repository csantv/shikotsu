package pe.tcloud.shikotsu.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.medical.model.PatientPreliminaryHistory;

import java.util.UUID;

public interface PatientPreliminaryHistoryRepository extends JpaRepository<PatientPreliminaryHistory, UUID> {
}
