package meecrowave.controller;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import meecrowave.exception.ApplicationException;
import meecrowave.model.Person;
import meecrowave.model.PersonRequest;

@Path("/")
@ApplicationScoped
public class PersonController {
    
    private static final Logger log = Logger.getLogger(PersonController.class.getName());
    private static final JsonProvider json = JsonProvider.provider();
    
    @Inject
    @ConfigProperty(name = "com.example.test", defaultValue = "sankar")
    private String name;
    
    @GET
    @Path("hello/test")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject sayHello() {
        return json.createObjectBuilder()
                       .add("person_age", 20)
                       .add("person_name", name)
                           .add("contact", json.createObjectBuilder()
                               .add("number", "+91834834561")
                               .add("email", "test@test.com")
                               .build())
                       .build();
    }

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Person sayHello(@QueryParam("test") @NotNull @Pattern(regexp = "[A-Z]") String test) {
       
        Person person = new Person();
        person.setPersonAge(12);
        person.setPersonName("David");
        if(1==1) {
            throw new ApplicationException(501, "errorMessage");
        }
        return person;
    }
    
    @POST
    @Path("hello")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person sayHello(@QueryParam("test") @Pattern(regexp = "[A-Z]") String test, @Valid PersonRequest personRequest) {
        log.info(test);
        log.info(personRequest.getName());
        Person person = new Person();
        person.setPersonAge(12);
        person.setPersonName("David");
        
        return person;
    }
}
