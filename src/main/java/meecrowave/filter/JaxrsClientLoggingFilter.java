package meecrowave.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Provider
public class JaxrsClientLoggingFilter implements ClientResponseFilter, ClientRequestFilter {
	
	private static final String REQUEST_START_TIME = "requestStartTime";

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		requestContext.setProperty(REQUEST_START_TIME,  System.currentTimeMillis());
	}

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		//if (responseContext.getEntityStream() != null && responseContext.getStatus() > 399) {
			/*final String response = read(new BufferedInputStream(responseContext.getEntityStream()));
			responseContext.setEntityStream(new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8)));
			log.info(response);*/
		//}
		log.info("Uri : {}, method : {}, status: {}, time : {}", requestContext.getUri(), 
				requestContext.getMethod(), responseContext.getStatus(), 
				(System.currentTimeMillis() - (Long)requestContext.getProperty(REQUEST_START_TIME)));
	}
	
	public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
