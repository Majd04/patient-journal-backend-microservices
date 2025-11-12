package se.kth.lab2.service;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import se.kth.lab2.dto.PatientDTO;
import se.kth.lab2.entity.PatientSearchEntry;

@ApplicationScoped
public class PatientSearchConsumer {

    @Incoming("patient-events")
    @Blocking
    @Transactional
    public void consumePatientEvent(PatientDTO patient) {
        System.out.println("Mottog patient-händelse: " + patient.firstName);
        PatientSearchEntry.addOrUpdate(patient);
    }

    // TODO: Skapa en till @Incoming("journal-events") för att hantera journalevent
}