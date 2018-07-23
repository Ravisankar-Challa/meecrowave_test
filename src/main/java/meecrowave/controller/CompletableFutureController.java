package meecrowave.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.spi.JsonbProvider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import meecrowave.model.Data;
import meecrowave.service.DataService;

@Path("/")
@ApplicationScoped
@NoArgsConstructor
@SuppressWarnings("serial")
@AllArgsConstructor(onConstructor = @__(@Inject))
public class CompletableFutureController {
    
    private DataService miscellaneousInfoService;
    private static final Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Path("async/controller")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Data> fetch() {
        return miscellaneousInfoService.fetchMiscellaneousInfo2();
    }
    
    @GET
    @Path("async/controller1")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<List<Data>> fetch1() {
        CompletionStage<Data> c1 = miscellaneousInfoService.fetchMiscellaneousInfo2();
        CompletionStage<Data> c2 = miscellaneousInfoService.fetchMiscellaneousInfo2();
        CompletionStage<Data> c3 = miscellaneousInfoService.fetchMiscellaneousInfo2();
        return c2.thenCombine(c1, (x1, x2) -> new ArrayList<Data>(){{ add(x1); add(x2); }})
                 .thenCombine(c3, (x1, x2) -> { x1.add(x2); return x1; });
    }
}
