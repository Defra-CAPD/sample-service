package uk.gov.defra.capd.sample.resources;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.defra.capd.sample.api.SampleResponse;
import uk.gov.defra.capd.sample.api.Params;
import uk.gov.defra.capd.sample.domain.Sample;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;


@Path("/samples")
public class SampleResource {

    private static Logger LOGGER = LoggerFactory.getLogger(SampleResource.class);

    public SampleResource() {  }

    @GET
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response get(@HeaderParam(Params.CALLER_ID) BigInteger callerId, @PathParam("id") BigInteger id) {

        SampleResponse s = new SampleResponse(new Sample(1));
        LOGGER.debug("Caller " + callerId + " requested sample " + id);

        return Response.ok(s).build();
    }

}
