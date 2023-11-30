package pe.tcloud.shikotsu.document.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.tcloud.shikotsu.document.model.Document;
import pe.tcloud.shikotsu.document.service.DocumentService;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentWriteController {
    private final DocumentService documentService;

    public DocumentWriteController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/new")
    public ResponseEntity<Document> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("filename") String filename) {
        byte[] fileBytes;
        Document doc;
        try {
            fileBytes = file.getBytes();
            doc = documentService.uploadDocument(filename, fileBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        return ResponseEntity.ok(doc);
    }
}
