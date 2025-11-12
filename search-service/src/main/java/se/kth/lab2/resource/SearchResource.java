package se.kth.lab2.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import se.kth.lab2.entity.PatientSearchEntry;

import java.util.List;

@Path("/api/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    @GET
    @Path("/patients")
    public List<PatientSearchEntry> searchPatients(@QueryParam("query") String query) {
        if (query == null || query.trim().isEmpty()) {
            return PatientSearchEntry.listAll();
        }

        String likeQuery = "%" + query.toLowerCase() + "%";
        return PatientSearchEntry.list("fullTextSearch LIKE ?1", likeQuery);
    }

    // TODO: Implementera sökning för läkare etc.
}