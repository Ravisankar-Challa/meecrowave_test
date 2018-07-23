package meecrowave.util;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

@Provider
public class GZIPDecoder implements ReaderInterceptor {

    private static final String GZIP = "gzip";
    private static final String CONTENT_ENCODING = "Content-Encoding";

    public Object aroundReadFrom(ReaderInterceptorContext ctx) throws IOException {
		String encoding = ctx.getHeaders().getFirst(CONTENT_ENCODING);
		if (encoding == null || !encoding.toLowerCase().contains(GZIP)) {
			return ctx.proceed();
		}
		ctx.setInputStream(new GZIPInputStream(ctx.getInputStream()));
		return ctx.proceed();
	}
}
