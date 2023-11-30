package pe.tcloud.shikotsu.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.tcloud.shikotsu.document.model.Document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findAllByPatientPatientId(UUID patientId);
    Optional<Document> findByDocumentIdAndFilename(UUID documentId, String filename);
}
