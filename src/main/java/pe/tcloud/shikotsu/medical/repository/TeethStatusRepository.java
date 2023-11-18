package pe.tcloud.shikotsu.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.medical.model.TeethStatus;

import java.util.UUID;

public interface TeethStatusRepository extends JpaRepository<TeethStatus, UUID> {
}
