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
    includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SIHbasePrototypebackend.sih.repo.UserRepository.class),
    entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "userTransactionManager"
)
public class UserDBConfig {

    @Bean
    @ConfigurationProperties("user.datasource")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "userDataSource")
    public DataSource userDataSource() {
        return userDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("userDataSource") DataSource dataSource) {

        return builder
            .dataSource(dataSource)
            .packages("SIHbasePrototypebackend.sih.model") // User is here
            .persistenceUnit("user")
            .build();
    }

    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager userTransactionManager(
        @Qualifier("userEntityManagerFactory") EntityManagerFactory emf) {

        return new JpaTransactionManager(emf);
    }
}