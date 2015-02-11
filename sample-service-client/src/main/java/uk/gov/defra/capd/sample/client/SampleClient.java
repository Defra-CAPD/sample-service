package uk.gov.defra.capd.sample.client;

import com.fasterxml.jackson.core.type.TypeReference;
import uk.gov.defra.capd.sample.domain.Sample;

import java.math.BigInteger;
import java.net.URI;

import static javax.ws.rs.HttpMethod.GET;

public class SampleClient extends BaseClient {


    public SampleClient(String basePath) {
        super(basePath);
    }

    public Sample getSample(BigInteger sampleId, BigInteger callerId) {
        URI uri = servicesURIBuilder().path("/samples/" + sampleId).build();
        return getResponse(buildHeaders(callerId), null, uri, GET, new TypeReference<Sample>() {
        });
    }
}
