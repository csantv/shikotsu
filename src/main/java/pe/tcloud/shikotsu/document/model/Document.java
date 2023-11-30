package pe.tcloud.shikotsu.document.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pe.tcloud.shikotsu.medicalhr.model.Patient;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "document")
public class Document {
    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID documentId = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String filename;

    private String hash;

    private String downloadUrl;

    private String mediaType;

    @CreationTimestamp
    private Instant auditCreate;

    @Transient
    private byte[] bytes;
}
