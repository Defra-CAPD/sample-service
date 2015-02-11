package uk.gov.defra.capd.sample.client.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.defra.capd.jersey.client.apache.HttpClientException;

import java.io.IOException;

public class JsonSerializer {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static String serialize(Object obj) {
        try {
            if (obj != null) {
                return mapper.writeValueAsString(obj);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new HttpClientException(500, e.getMessage(), e);
        }
    }

    public static <T> T deserialize(String response, TypeReference<T> typeReference) {
        if (response != null) {
            try {
                return mapper.readValue(response, typeReference);
            } catch (IOException e) {
                throw new HttpClientException(500, e.getMessage(), e);
            }
        } else {
            return null;
        }
    }
}