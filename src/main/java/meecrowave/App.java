package meecrowave;

import javax.json.bind.config.PropertyNamingStrategy;

import org.apache.meecrowave.Meecrowave;
import org.apache.meecrowave.Meecrowave.Builder;

public class App {
    public static void main( String[] args ) {
        Builder builder = new Builder();
        //builder.setHttpPort(8082);
        builder.setJaxrsAutoActivateBeanValidation(true);
        //builder.setJaxrsLogProviders(true);
        //builder.setScanningPackageIncludes("meecrowave");
        //builder.setJsonbNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);
        builder.setJsonbNulls(true);
        new Meecrowave(builder).bake().await();
    }
}
