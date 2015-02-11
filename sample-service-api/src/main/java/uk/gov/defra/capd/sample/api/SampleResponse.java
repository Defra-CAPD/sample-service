package uk.gov.defra.capd.sample.api;

import uk.gov.defra.capd.sample.domain.Sample;

public class SampleResponse {

    private Sample sample;

    public Sample getSample() {
        return sample;
    }

    public SampleResponse(Sample sample) {
        this.sample = sample;
    }
}
