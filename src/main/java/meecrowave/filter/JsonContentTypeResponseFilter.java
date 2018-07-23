package meecrowave.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonContentTypeResponseFilter implements ClientResponseFilter {

	@SuppressWarnings("serial")
	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		responseContext.getHeaders().put("Content-Type", new ArrayList<String>() {{
			//Override the content type in response message to application/json
			add(MediaType.APPLICATION_JSON);
		}});
	}
}