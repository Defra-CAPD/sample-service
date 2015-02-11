package uk.gov.defra.capd.sample.resources;

import com.codahale.metrics.annotation.Timed;
import uk.gov.defra.capd.common.healthcheck.ApplicationStatusService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/status")
public class StatusResource {

    private final ApplicationStatusService applicationStatusService;
    private final String applicationName;

    public StatusResource(ApplicationStatusService applicationStatusService, String applicationName) {
        this.applicationStatusService = applicationStatusService;
        this.applicationName = applicationName;
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
        return applicationStatusService.getStatus(applicationName);
    }
}