package meecrowave.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
@AllArgsConstructor(onConstructor = @__(@Inject))
public class DataInfoController {

    private DataService miscellaneousInfoService;

    @GET
    @Path("misc/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Data fetchMiscellaneousInfo() {
        return miscellaneousInfoService.fetchMiscellaneousInfo1();
    }

}
