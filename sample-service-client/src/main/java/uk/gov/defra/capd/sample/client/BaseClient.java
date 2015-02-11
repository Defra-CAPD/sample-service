package uk.gov.defra.capd.sample.client;

import com.fasterxml.jackson.core.type.TypeReference;
import uk.gov.defra.capd.jersey.client.apache.HttpClient;
import uk.gov.defra.capd.jersey.client.apache.HttpClientException;
import uk.gov.defra.capd.sample.api.Params;
import uk.gov.defra.capd.sample.client.exceptions.ServicesClientException;
import uk.gov.defra.capd.sample.client.json.JsonSerializer;

import javax.ws.rs.core.UriBuilder;
import java.math.BigInteger;
import java.net.URI;
import java.util.HashMap;

public class BaseClient {

    protected final HttpClient httpClient = new HttpClient();
    private final String basePath;

    public BaseClient(String basePath) {
        this.basePath = basePath;
    }

    protected <T> T getResponse(HashMap<String, String> headerParams, Object body, URI uri, String method, TypeReference<T> typeReference) {

        try {
            String response = httpClient.invokeAPI(uri, method, JsonSerializer.serialize(body), headerParams);
            return JsonSerializer.deserialize(response, typeReference);
        } catch (HttpClientException ex) {
            throw new ServicesClientException(ex);
        }

    }

    protected void invoke(HashMap<String, String> headerParams, URI uri, String method) {

        try {
            httpClient.invokeAPI(uri, method, null, headerParams);
        } catch (HttpClientException ex) {
            throw new ServicesClientException(ex);
        }

    }

    protected UriBuilder servicesURIBuilder() {
        return UriBuilder.fromPath(basePath);
    }

    protected HashMap<String, String> buildHeaders(BigInteger callerId) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(Params.CALLER_ID, callerId.toString());
        return headers;
    }
}