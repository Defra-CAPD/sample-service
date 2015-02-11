package uk.gov.defra.capd.sample.health;

import com.codahale.metrics.health.HealthCheck;

public class SampleHealthcheck extends HealthCheck {

    public SampleHealthcheck() { }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
