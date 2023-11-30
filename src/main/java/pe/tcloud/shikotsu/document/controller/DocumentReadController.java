package pe.tcloud.shikotsu.document.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.tcloud.shikotsu.document.model.Document;
import pe.tcloud.shikotsu.document.repository.DocumentRepository;
import pe.tcloud.shikotsu.document.service.DocumentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentReadController {
    private final DocumentRepository documentRepository;
    private final DocumentService documentService;

    public DocumentReadController(DocumentRepository documentRepository,
                                  DocumentService documentService) {
        this.documentRepository = documentRepository;
        this.documentService = documentService;
    }

    @GetMapping("/getByPatientId/{patientId}")
    public ResponseEntity<List<Document>> getByPatientId(@PathVariable UUID patientId) {
        var docs = documentRepository.findAllByPatientPatientId(patientId);
        return ResponseEntity.ok(docs);
    }

    @GetMapping("/blob/{uuid}/{filename}")
    public ResponseEntity<ByteArrayResource> getDocBlob(@PathVariable UUID uuid, @PathVariable String filename) {
        Optional<Document> docDb;
        try {
            docDb = documentService.getDocument(uuid, filename);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (docDb.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var document = docDb.get();
        var responseBody = new ByteArrayResource(document.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename(document.getFilename()).build());
        headers.setContentType(MediaType.parseMediaType(document.getMediaType()));
        headers.setContentLength(document.getBytes().length);
        return ResponseEntity.ok()
                .headers(headers)
                .body(responseBody);
    }
}
