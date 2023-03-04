package com.springmongotests.demo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.springmongotests.demo.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    String mongoUri;

    private final static String dbName = "test";

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    protected MongoClientSettings mongoClientSettings() {
        final ConnectionString connectionString = new ConnectionString(mongoUri);
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
