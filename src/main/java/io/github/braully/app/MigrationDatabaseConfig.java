package io.github.braully.app;

import io.github.braully.util.logutil;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MigrationDatabaseConfig implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    DataSource dataSource;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent e) {
        try {
            logutil.info("Manual migrate flyway -- post app ready");
            ClassicConfiguration configuration = new ClassicConfiguration();
            configuration.setDataSource(dataSource);
            configuration.setOutOfOrder(true);
            configuration.setValidateOnMigrate(false);
            configuration.setLocationsAsStrings("classpath:db/migration-data");
            Flyway flywaytmp = new Flyway(configuration);
            flywaytmp.migrate();
        } catch (Exception ex) {
            logutil.error("Fail on flyway automate", ex);
        }
    }

    // Database
    /*
     * Flyway Database configuration Workaround: Force flyway running post hibernate
     * schema update
     * https://stackoverflow.com/questions/37097876/spring-boot-hibernate-and-flyway
     * -boot-order
     */
    @Bean
    @Primary
    /* Execute before hibernate schema update, configuration em application.properties */
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        // ((ClassicConfiguration) flyway.getConfiguration()).setOutOfOrder(true);
        return new FlywayMigrationInitializer(flyway);
    }
}
