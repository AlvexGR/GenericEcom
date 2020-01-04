package com.nhannn.generic_ecom.configs;

import com.nhannn.generic_ecom.helpers.Encryption;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Author: nhannn
 */
@Configuration
public class DataSourceConfiguration {
    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .url(Encryption.decrypt("1KQHdmlqwofR9d42C/prWQ==:2FfHmk3ligovEUl+VyIKX5g12H04Vyd1Ibmv8sklcJ8aVuB02coM+nGQjxjG+e/K13IjeFt4Q/o9SF2d0m0Wjol25jvsnBzC6SgXfhK2JmgBOnu4wy45bUjzcgMN83V+"))
                .username(Encryption.decrypt("y4oXdkRyb05xIfHPY3F58Q==:IJrUPPwx6fvrfojOpQzEmA=="))
                .password(Encryption.decrypt("WUPyo/MkhuFF5KarDWqtZA==:L1eQm0G2IFcZnbYiQKSp3A=="))
                .build();
    }
}
