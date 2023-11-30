package pe.tcloud.shikotsu.document.service;

import org.apache.tika.Tika;
import org.bouncycastle.crypto.digests.Blake2spDigest;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pe.tcloud.shikotsu.config.ShikotsuConfiguration;
import pe.tcloud.shikotsu.document.model.Document;
import pe.tcloud.shikotsu.document.repository.DocumentRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
    private final S3Service s3Service;
    private final String baseUrl;
    private final DocumentRepository documentRepository;
    private final Tika tika;

    public DocumentService(S3Service s3Service,
                           ShikotsuConfiguration shikotsuConfiguration,
                           DocumentRepository documentRepository) {
        this.s3Service = s3Service;
        this.baseUrl = shikotsuConfiguration.baseUrl();
        this.documentRepository = documentRepository;
        this.tika = new Tika();
    }

    private String getBlake2BHash(byte[] bytes) {
        var digest = new Blake2spDigest(null);
        digest.update(bytes, 0, bytes.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return Hex.toHexString(hash);
    }

    public Document uploadDocument(String filename, byte[] fileData) {
//        var exists = checkIfDocumentExists(fileName, fileData);
//        if (exists.isPresent()) {
//            return exists.get();
//        }
        var doc = new Document();
        s3Service.putObject(fileData, doc.getDocumentId().toString(), filename);
        String hashStr = getBlake2BHash(fileData);
        var builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        var uri = builder
                .pathSegment("api", "v1", "document", "blob", "{id}", "{filename}")
                .build(doc.getDocumentId(), filename);
        doc.setDownloadUrl(uri.toString());
        doc.setFilename(filename);
        doc.setMediaType(tika.detect(fileData));
        doc.setHash(hashStr);
        return documentRepository.save(doc);
    }

    public Optional<Document> getDocument(UUID id, String filename) throws IOException {
        var dbDocument = documentRepository.findByDocumentIdAndFilename(id, filename);
        if (dbDocument.isEmpty()) {
            return Optional.empty();
        }
        var bytes = s3Service.getObject(id.toString(), filename);
        var doc = dbDocument.get();
        doc.setBytes(bytes);
        return Optional.of(doc);
    }
}
