package com.example.mongodemo.config;

import com.example.mongodemo.repo.PersonRepo;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(/*basePackages = "com.baeldung.repository")*/basePackageClasses = PersonRepo.class, mongoTemplateRef = "primaryMongoTemplate")
@EnableConfigurationProperties
public class MongoConfig {
    @Value("${spring.data.mongodb.primary.host}")
    private String host;
    @Value("${spring.data.mongodb.primary.port}")
    private Integer port;
    @Value("${spring.data.mongodb.primary.database}")
    private String database;

    @Bean(name = "primaryProperties")
    @ConfigurationProperties(prefix = "mongodb.primary")
    @Primary
    public MongoProperties primaryProperties() {
        MongoProperties mongoProperties = new MongoProperties();
        mongoProperties.setHost(host);
        mongoProperties.setPort(port);
        mongoProperties.setDatabase(database);
        return mongoProperties;
    }

    @Bean(name = "primaryMongoClient")
    public MongoClient mongoClient(@Qualifier("primaryProperties") MongoProperties mongoProperties) {

//        MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());
        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        builder.applyToClusterSettings(builder1 -> builder1.hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))));
//        builder.credential(credential);
        MongoClientSettings mongoClientSettings = builder.build();

        return MongoClients.create(mongoClientSettings);
    }

    @Primary
    @Bean(name = "primaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("primaryMongoClient") MongoClient mongoClient, @Qualifier("primaryProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("primaryMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean
    public MongoTransactionManager transactionManager(@Qualifier("primaryMongoDBFactory") MongoDatabaseFactory mongoDatabase) {
        return new MongoTransactionManager(mongoDatabase);
    }
}
