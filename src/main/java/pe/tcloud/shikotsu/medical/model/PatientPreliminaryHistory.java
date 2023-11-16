package pe.tcloud.shikotsu.medical.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pe.tcloud.shikotsu.medicalhr.model.Doctor;
import pe.tcloud.shikotsu.medicalhr.model.Patient;
import pe.tcloud.shikotsu.tenant.model.Company;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "patient_preliminary_history")
public class PatientPreliminaryHistory {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID patientPreliminaryHistoryId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(insertable = false, updatable = false)
    private long historyNumber;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @CreationTimestamp
    private Instant auditCreate;

    @UpdateTimestamp
    private Instant auditUpdate;
}