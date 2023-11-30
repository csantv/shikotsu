package pe.tcloud.shikotsu.document.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pe.tcloud.shikotsu.document.model.Document;
import pe.tcloud.shikotsu.document.repository.DocumentRepository;
import pe.tcloud.shikotsu.document.service.DocumentService;
import pe.tcloud.shikotsu.medicalhr.repository.PatientRepository;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentWriteController {
    private final DocumentService documentService;
    private final PatientRepository patientRepository;
    private final DocumentRepository documentRepository;

    public DocumentWriteController(DocumentService documentService,
                                   PatientRepository patientRepository,
                                   DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.patientRepository = patientRepository;
        this.documentRepository = documentRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<Document> uploadFile(@RequestParam("file") MultipartFile file,
                                               @RequestParam("filename") String filename,
                                               @RequestParam("patientId") UUID patientId) {
        var patient = patientRepository.findById(patientId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        byte[] fileBytes;
        Document doc;
        try {
            fileBytes = file.getBytes();
            doc = documentService.uploadDocument(filename, fileBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        doc.setPatient(patient);
        doc = documentRepository.save(doc);
        return ResponseEntity.ok(doc);
    }
}
