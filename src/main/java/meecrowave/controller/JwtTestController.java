package meecrowave.controller;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/")
@ApplicationScoped
//@AllArgsConstructor(onConstructor = @__(@Inject))
public class JwtTestController {

    @Inject private JsonWebToken jwt;
    @Inject @Claim(standard = Claims.raw_token) private String rawToken;
    @Inject @Claim(standard = Claims.iat) private Long issuedAt;
    @Inject @Claim(standard = Claims.sub) private ClaimValue<Optional<String>> subject;
    @Inject @Claim(standard = Claims.auth_time) private ClaimValue<Optional<Long>> authTime;
    
    @GET
    @Path("jwt")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        System.out.println(jwt.getName()+rawToken+issuedAt);
        return "upn: " + jwt.getName() + " iss: " + issuedAt + " " + rawToken;
    }
}
