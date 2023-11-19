package pe.tcloud.shikotsu.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.finances.model.Service;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
}
