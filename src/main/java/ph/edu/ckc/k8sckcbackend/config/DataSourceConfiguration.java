package ph.edu.ckc.k8sckcbackend.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSourceConfiguration {

    private final Environment environment;

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource customDataSource() {
          return DataSourceBuilder.create()
                .username(environment.getProperty("POSTGRES_USERNAME"))
                .password(environment.getProperty("POSTGRES_PASSWORD"))
                .url("jdbc:postgresql://" + environment.getProperty("POSTGRES_HOST")+":"+environment.getProperty("POSTGRES_PORT") +"/"+ environment.getProperty("POSTGRES_DATABASE"))
                .build();
    }
}
