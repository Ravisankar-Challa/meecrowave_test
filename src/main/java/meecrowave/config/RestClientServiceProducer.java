package meecrowave.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.client.ClientBuilder;

import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.johnzon.jaxrs.jsonb.jaxrs.JsonbJaxrsProvider;

import lombok.extern.log4j.Log4j2;
import meecrowave.filter.JaxrsClientLoggingFilter;
import meecrowave.filter.JsonContentTypeResponseFilter;
import meecrowave.service.RestClientService;
import meecrowave.util.GZIPDecoder;

@Log4j2
@ApplicationScoped
public class RestClientServiceProducer {
    
    public RestClientServiceProducer() {
        log.info("Indside RestClientServiceProducer constructor");
    }

    @PostConstruct
    public void init() {
        log.info("Indside RestClientServiceProducer PostConstruct");
    }

    @Produces
    @RestClient
    public RestClientService buildHttpClientService(InjectionPoint ip) {
        boolean useProxy = ip.getAnnotated().getAnnotation(RestClient.class).useProxy();
        int readTimeout = ip.getAnnotated().getAnnotation(RestClient.class).readTimeout();
        int connectTimeout = ip.getAnnotated().getAnnotation(RestClient.class).connectTimeout();
        log.info("Creating the JAX-RS rest client userProxy : {} readTimeout : {} connectTimeout : {}", useProxy,
                readTimeout, connectTimeout);
        ClientBuilder clientBuilder = ClientBuilder.newBuilder()
                                                   .readTimeout(readTimeout, TimeUnit.SECONDS)
                                                   .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                                                   .register(JsonbJaxrsProvider.class)
                                                   .register(GZIPDecoder.class)
                                                   .register(JaxrsClientLoggingFilter.class)
                                                   .register(LoggingFeature.class)
                                                   .register(JsonContentTypeResponseFilter.class);
        if (useProxy) {
            clientBuilder.property("http.proxy.server.uri", System.getProperty("https.proxyHost"));
            clientBuilder.property("http.proxy.server.port", System.getProperty("https.proxyPort"));
            clientBuilder.property("com.ibm.ws.jaxrs.client.proxy.host",
                    System.getProperty("https.proxyHost"));
            clientBuilder.property("com.ibm.ws.jaxrs.client.proxy.port",
                    System.getProperty("https.proxyPort"));
        }
        return new RestClientService(clientBuilder.build());
    }
    
    @PreDestroy
    public void destroy() {
        log.info("Indside RestClientServiceProducer destroy");
    }

}
