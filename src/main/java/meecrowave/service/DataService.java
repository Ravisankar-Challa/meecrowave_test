package meecrowave.service;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.johnzon.jaxrs.jsonb.jaxrs.JsonbJaxrsProvider;

import lombok.NoArgsConstructor;
import meecrowave.config.RestClient;
import meecrowave.filter.JaxrsClientLoggingFilter;
import meecrowave.filter.JsonContentTypeResponseFilter;
import meecrowave.model.Data;
import meecrowave.util.GZIPDecoder;

@ApplicationScoped
@NoArgsConstructor
public class DataService {

    protected static final String url = "https://raw.githubusercontent.com/Ravisankar-Challa/tomee_embedded/master/src/main/resources/data.js";

    private RestClientService restClientService;
    
    private static final Client client = ClientBuilder.newBuilder()
            .readTimeout(10, SECONDS)
            .connectTimeout(3, SECONDS)
            .register(JsonbJaxrsProvider.class)
            .register(GZIPDecoder.class)
            .register(JaxrsClientLoggingFilter.class)
            //.register(LoggingFeature.class)
            .register(JsonContentTypeResponseFilter.class)
            .build();

    @Inject
    public DataService(@RestClient(connectTimeout = 2000, readTimeout = 15000) 
            final RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    public Data fetchMiscellaneousInfo1() {
        // return httpClientService.doGet(url, new HashMap<>() {{ put("param","param1");
        // }}, new HashMap<>() {{ put("param","param1"); }}, Data.class);
        return restClientService.doGet(url, null, null, Data.class);
    }
    
    public CompletionStage<Data> fetchMiscellaneousInfo2() {
        return client.target(url)
                     .request()
                     .header("Accept-Encoding", "gzip")
                     .rx()
                     .get(Data.class);
    }

}
