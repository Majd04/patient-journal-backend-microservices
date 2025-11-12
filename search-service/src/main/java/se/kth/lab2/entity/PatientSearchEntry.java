package se.kth.lab2.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import se.kth.lab2.dto.PatientDTO;

@Entity
public class PatientSearchEntry extends PanacheEntity {
    @Column(unique = true)
    public Long originalPatientId;

    public String firstName;
    public String lastName;

    @Column(columnDefinition = "TEXT")
    public String fullTextSearch;

    public static void addOrUpdate(PatientDTO dto) {
        PatientSearchEntry entry = find("originalPatientId", dto.id).firstResult();
        if (entry == null) {
            entry = new PatientSearchEntry();
            entry.originalPatientId = dto.id;
        }

        entry.firstName = dto.firstName;
        entry.lastName = dto.lastName;
        entry.fullTextSearch = (dto.firstName + " " + dto.lastName + " " + dto.personalNumber + " " + dto.email).toLowerCase();

        entry.persist();
    }
}