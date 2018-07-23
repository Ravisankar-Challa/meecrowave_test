package meecrowave.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.meecrowave.jpa.api.PersistenceUnitInfoBuilder;
import org.h2.Driver;

import meecrowave.entity.Member;

@ApplicationScoped
public class JpaConfig {
    @Produces // dbcp2 datasource for instance
    @ApplicationScoped
    public DataSource dataSource() {
        final BasicDataSource source = new BasicDataSource();
        source.setDriver(new Driver());
        source.setUrl("jdbc:h2:mem:jpaextensiontest");
        return source;
    }
    
    @Produces
    public PersistenceUnitInfoBuilder unit(final DataSource ds) throws MalformedURLException {
    	Properties properties = new Properties();
    	properties.setProperty("eclipselink.composite-unit.member", "true");
        return new PersistenceUnitInfoBuilder()
                .setUnitName("meecrowave_test")
                //.setProviderClass("org.eclipse.persistence.jpa.PersistenceProvider")
                //.setRootUrl(new URL("persistence.xml"))
                .setDataSource(ds)
                .setExcludeUnlistedClasses(true)
                .setProperties(properties)
                .addManagedClazz(Member.class)
                .addProperty("openjpa.RuntimeUnenhancedClasses", "supported")
                .addProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema");
    }
}
