package uk.gov.defra.capd.sample.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SampleServiceConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private ApplicationConfig applicationConfig = new ApplicationConfig();

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }
}
