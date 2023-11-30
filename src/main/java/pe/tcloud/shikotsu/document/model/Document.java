package pe.tcloud.shikotsu.document.model;

import jakarta.persistence.*;
import lombok.*;
import pe.tcloud.shikotsu.medicalhr.model.Patient;

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
    @GeneratedValue
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

    @Transient
    private byte[] bytes;
}
