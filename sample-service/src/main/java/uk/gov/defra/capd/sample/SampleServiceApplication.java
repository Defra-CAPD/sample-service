package uk.gov.defra.capd.sample;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.gov.defra.capd.common.healthcheck.ApplicationStatusService;
import uk.gov.defra.capd.sample.config.SampleServiceConfiguration;
import uk.gov.defra.capd.sample.health.SampleHealthcheck;
import uk.gov.defra.capd.sample.resources.SampleResource;
import uk.gov.defra.capd.sample.resources.StatusResource;


public class SampleServiceApplication extends Application<SampleServiceConfiguration> {

    private static final String SAMPLE_SERVICE = "sample-service";

    public static void main(String[] args) throws Exception {
        new SampleServiceApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<SampleServiceConfiguration> bootstrap) {
    }

    @Override
    public void run(SampleServiceConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new SampleResource());
        environment.jersey().register(new StatusResource(createApplicationStatusService(configuration), getName()));
        environment.healthChecks().register("sample-service", new SampleHealthcheck());
    }

    private ApplicationStatusService createApplicationStatusService(SampleServiceConfiguration configuration) {
        final ApplicationStatusService applicationStatusService = new ApplicationStatusService();
        applicationStatusService.setApplicationVersionFilePath(configuration.getApplicationConfig().getApplicationVersionFilePath());
        applicationStatusService.initialize();
        return applicationStatusService;
    }

    @Override
    public String getName() {
        return SAMPLE_SERVICE;
    }
}
