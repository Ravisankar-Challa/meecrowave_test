package meecrowave.service;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static javax.ws.rs.core.Response.Status.Family.familyOf;
import static meecrowave.util.Constants.X_CORRELATION_ID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.ThreadContext;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meecrowave.exception.RequestTimeOutException;
import meecrowave.exception.UnsuccesfulResponseException;
import meecrowave.model.ResponseStats;
import meecrowave.model.Time;

@Log4j2
@AllArgsConstructor
public class RestClientService {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String MILLI_SECS = "ms";

    private Client client;

    public <T> T doGet(final String url, final Map<String, Object> urlParams, final Map<String, Object> headers,
            final Class<T> responseClazzType) {
        return execute(HttpMethod.GET, url, urlParams, headers, headers, null, responseClazzType);
    }

    public <T> T doPost(final String url, final Map<String, Object> headers, final Object body,
            final Class<T> responseClazzType) {
        return execute(HttpMethod.POST, url, null, null, headers, body, responseClazzType);
    }

    protected <T> T execute(final String httpMethod, final String url, final Map<String, Object> urlParameters,
            final Map<String, Object> queryParameters, final Map<String, Object> headers, final Object body,
            final Class<T> responseClazzType) {
        String uri = url;
        Response response = null;
        long requestStartTime = System.currentTimeMillis();
        try {
            UriBuilder uriBuilder = UriBuilder.fromUri(url);
            Optional.ofNullable(queryParameters).ifPresent(queryParams -> queryParams.forEach(uriBuilder::queryParam));
            uri = (urlParameters == null) ? uriBuilder.buildFromMap(new HashMap<String, Object>()).toString()
                                          : uriBuilder.buildFromMap(urlParameters).toString();
            Builder requestBuilder = client.target(uri).request();
            requestBuilder.header(CONTENT_TYPE, MediaType.APPLICATION_JSON);
            requestBuilder.header(X_CORRELATION_ID, ThreadContext.get(X_CORRELATION_ID));
            Optional.ofNullable(headers).ifPresent(headerParams -> headerParams.forEach(requestBuilder::header));
            response = (body != null) ? requestBuilder.method(httpMethod, Entity.json(body))
                                               : requestBuilder.method(httpMethod);
            if (SUCCESSFUL == familyOf(response.getStatus())) {
                logResponseStats(null, response.getStatus(), uri, httpMethod, requestStartTime);
                return response.readEntity(responseClazzType);
            } else {
                final String responseString = response.readEntity(String.class);
                logResponseStats(responseString, response.getStatus(), uri, httpMethod, requestStartTime);
                log.error(responseString);
                throw new UnsuccesfulResponseException(responseString, response.getStatus());
            }
        } catch (ProcessingException poe) {
            log.error(poe.getMessage(), poe);
            throw new RequestTimeOutException(poe);
        } finally {
            response.close();
        }
    }

    protected void logResponseStats(final String errorString, final int statusCode, final String uri,
            final String httpMethod, final long requestStartTime) {
        ResponseStats responseStats = ResponseStats.builder()
                                                   .status(statusCode)
                                                   .time(Time.builder()
                                                             .value(System.currentTimeMillis() - requestStartTime)
                                                             .units(MILLI_SECS)
                                                             .build())
                                                   .uri(uri)
                                                   .httpMethod(httpMethod)
                                                   .error(errorString)
                                                   .build();
        log.info(responseStats);
    }
}
