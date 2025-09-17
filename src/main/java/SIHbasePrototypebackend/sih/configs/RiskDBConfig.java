package SIHbasePrototypebackend.sih.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "SIHbasePrototypebackend.sih.repo",
    entityManagerFactoryRef = "riskEntityManagerFactory",
    transactionManagerRef = "riskTransactionManager"
)
public class RiskDBConfig {

    @Bean
    @ConfigurationProperties("risk.datasource")
    public DataSourceProperties riskDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "riskDataSource")
    public DataSource riskDataSource() {
        return riskDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "riskEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean riskEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("riskDataSource") DataSource dataSource) {

        return builder
            .dataSource(dataSource)
            .packages("SIHbasePrototypebackend.sih.model") // Your RiskRecord entity
            .persistenceUnit("risk")
            .build();
    }

    @Bean(name = "riskTransactionManager")
    public PlatformTransactionManager riskTransactionManager(
        @Qualifier("riskEntityManagerFactory") EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}