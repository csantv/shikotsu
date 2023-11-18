package pe.tcloud.shikotsu.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.medical.model.DentalChart;

import java.util.UUID;

public interface DentalChartRepository extends JpaRepository<DentalChart, UUID> {
}
