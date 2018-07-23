package meecrowave;

import javax.json.bind.config.PropertyNamingStrategy;
import org.apache.meecrowave.Meecrowave;
import org.apache.meecrowave.Meecrowave.Builder;

@SuppressWarnings("resource")
public class App {
	public static void main( String[] args ) {
	    //System.setProperty("geronimo.jwt-auth.kids.issuer.mapping", "your-256-bit-secret=Ravi");
	    //System.setProperty("geronimo.jwt-auth.header.alg.supported", "hs256");
	    System.setProperty("geronimo.jwt-auth.filter.active", "true");
        /*Builder builder = new Builder();
        //builder.setHttpPort(8082);
        builder.setJaxrsAutoActivateBeanValidation(true);
        builder.setJaxrsMapping("/api/*");
        //builder.setInjectServletContainerInitializer(true);
        //builder.setScanningPackageIncludes("meecrowave");
        //builder.setTomcatAutoSetup(true);
        builder.setTomcatScanning(true);
        //builder.setJaxrsLogProviders(true);
        //builder.setScanningPackageIncludes("meecrowave");
        //builder.setJsonbNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);
        builder.setJsonbNulls(true);
        //new Meecrowave(builder).bake().await();
*/        new Meecrowave(/*new Builder() {{
				           setJaxrsMapping("/api/*");
				           setJaxrsAutoActivateBeanValidation(true);
				           setJsonbNulls(true);
				           setTomcatScanning(true);
			         }}*/)
				    .bake()
				    .await();
    }
}
									