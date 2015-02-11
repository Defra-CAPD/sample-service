package uk.gov.defra.capd.sample.client.exceptions;

import uk.gov.defra.capd.jersey.client.apache.HttpClientException;

public class ServicesClientException extends RuntimeException {

    private int httpStatusCode;

    public ServicesClientException(HttpClientException exception) {
        super(exception);
        httpStatusCode = exception.getCode();
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
