package pe.tcloud.shikotsu.medicalhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import pe.tcloud.shikotsu.document.model.Document;
import pe.tcloud.shikotsu.tenant.model.Company;
import pe.tcloud.shikotsu.user.model.Person;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Patient {
    @Id
    @GeneratedValue
    private UUID patientId;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private boolean isActive = true;

    @OneToMany(mappedBy = "patient")
    private List<Document> documentList;
}
