package pe.tcloud.shikotsu.finances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.finances.model.Invoice;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
