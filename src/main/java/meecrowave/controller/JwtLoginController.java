package meecrowave.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.StringJoiner;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.spi.JsonProvider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@ApplicationScoped
//@AllArgsConstructor(onConstructor = @__(@Inject))
public class JwtLoginController {
    
    private static final JsonProvider jsonp = JsonProvider.provider();
    private static PrivateKey privKey;
    static {
        String privateKeyContent;
        try {
            privateKeyContent = new String(Files.readAllBytes(
                    Paths.get(ClassLoader.getSystemResource("meecrowave.key.pem").toURI())));
        privateKeyContent = privateKeyContent.replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        privKey = kf.generatePrivate(keySpecPKCS8);
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
    
    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject login() throws NoSuchAlgorithmException, IOException, URISyntaxException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        final String header = toJson(jsonp.createObjectBuilder().add("alg", "RS256")
                .add("kid", "test").build());
        final String body = toJson(jsonp.createObjectBuilder()
                                        .add("iss", "https://www.example.com")
                                        .add("sub", "Ravi")
                                        .add("jti", UUID.randomUUID().toString())
                                        .add("upn", "ravisankar2@gmail.com")
                                        .add("iat", System.currentTimeMillis() / 1000)
                                        .add("exp", System.currentTimeMillis() / 1000 + (60 * 60 * 24))
                                        .add("groups", jsonp.createArrayBuilder().add("Tester"))
                                        .build());
        final String encodedHeader = Base64.getUrlEncoder().encodeToString(header.getBytes());
        final String encodedBody = Base64.getUrlEncoder().encodeToString(body.getBytes());
        final Signature sig = Signature.getInstance("SHA256WithRSA");
        sig.initSign(privKey);
        sig.update((encodedHeader + "." + encodedBody).getBytes());
        final String signature = Base64.getUrlEncoder().encodeToString(sig.sign());
        String token = new StringJoiner(".").add(encodedHeader).add(encodedBody).add(signature).toString();
        return jsonp.createObjectBuilder().add("token", token).build();
    }

    private String toJson(JsonObject jsonObject) {
        StringWriter writer = new StringWriter();
        JsonWriter createWriter = jsonp.createWriter(writer);
        createWriter.write(jsonObject);
        createWriter.close();
        return writer.toString();
    }
}
