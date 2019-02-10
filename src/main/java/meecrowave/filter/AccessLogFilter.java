package meecrowave.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static meecrowave.util.Constants.X_CORRELATION_ID;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.ThreadContext;

import lombok.extern.log4j.Log4j2;
import meecrowave.model.AccessLog;

@Log4j2
@Provider
@PreMatching
@ApplicationScoped
public class AccessLogFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final String REQUEST_START_TIME = "requestStartTime";
	private static final String ZERO = "0";
    protected static final String X_FORWARDED_FOR = "X-Forwarded-For";
    protected static final String USER_AGENT = "User-Agent";
    protected static final String REFERER = "Referer";
    protected static final String CONTENT_LENGTH = "Content-Length";
    protected static final String EXPIRES = "Expires";
    protected static final String CACHE_CONTROL = "Cache-Control";
    protected static final String CACHE_CONTROL_VALEUS = "no-store, no-cache, must-revalidate, proxy-revalidate";
    protected static final String PRAGMA = "Pragma";
    protected static final String NO_CACHE = "no-cache";
    
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
	    final Optional<String> correlationId = Optional.ofNullable(requestContext.getHeaderString(X_CORRELATION_ID));
        ThreadContext.put(X_CORRELATION_ID, correlationId.orElse(UUID.randomUUID().toString()));
		responseContext.getHeaders().add(EXPIRES, ZERO);
		responseContext.getHeaders().add(CACHE_CONTROL, CACHE_CONTROL_VALEUS);
		responseContext.getHeaders().add(PRAGMA, NO_CACHE);
		log.info(buildAccessLog(requestContext, responseContext));
	}

	protected AccessLog buildAccessLog(final ContainerRequestContext requestContext, 
                                      final ContainerResponseContext responseContext) {
       return AccessLog.builder()
                       .srcIp("")
                       .xForwardedFor(requestContext.getHeaderString(X_FORWARDED_FOR))
                       .httpMethod(requestContext.getMethod())
                       .uri(requestContext.getUriInfo().getAbsolutePath().toString())
                       .protocol(requestContext.getUriInfo().getRequestUri().getScheme())
                       .status(responseContext.getStatus())
                       //.responseTime(System.currentTimeMillis() - (Long)requestContext.getProperty(REQUEST_START_TIME))
                       .userAgent(requestContext.getHeaderString(USER_AGENT))
                       .contentType(requestContext.getHeaderString("Content-Type"))
                       .referer(requestContext.getHeaderString(REFERER))
                       .build();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        requestContext.setProperty(REQUEST_START_TIME, System.currentTimeMillis());
        final Optional<String> correlationId = Optional.ofNullable(requestContext.getHeaderString(X_CORRELATION_ID));
        ThreadContext.put(X_CORRELATION_ID, correlationId.orElse(UUID.randomUUID().toString()));
    }
}
