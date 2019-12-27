package com.nhannn.generic_ecom.configs;

import com.nhannn.generic_ecom.helpers.encryptors.Encryption;
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
                .url(Encryption.decrypt("Qwv11mBxCKbzlTAaYq9EKQ==:JjyYIRGQ2lf2w067vRucpctlTjh1C+Okan5huHJOMP9k14EEnsLBeNz+E9Vpoz4IrL5KUhyT/kZfVoENtY+QFA=="))
                .username(Encryption.decrypt("y4oXdkRyb05xIfHPY3F58Q==:IJrUPPwx6fvrfojOpQzEmA=="))
                .password(Encryption.decrypt("JH8C6Q1Sql7/gTdBBLBJtw==:aL3uR85wRmId2OBovPVnAQ=="))
                .build();
    }
}
