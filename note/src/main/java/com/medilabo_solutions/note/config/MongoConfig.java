package com.medilabo_solutions.note.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for MongoDB database settings in the application.
 * This class configures the connection to MongoDB using MongoClient and provides access to the database.
 */
@Configuration
public class MongoConfig {

    /**
     * The MongoDB URI for connecting to the database.
     * This value is injected from the application properties (e.g., `application.yml` or `application.properties`).
     */
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    /**
     * Creates a {@link MongoClient} bean that connects to the MongoDB instance using the provided URI.
     * The client is configured to use the MongoDB Server API version 1.
     *
     * @return a {@link MongoClient} instance for connecting to MongoDB
     */
    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(mongoUri))
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                .build();
        return MongoClients.create(settings);
    }

    /**
     * Creates a {@link MongoDatabase} bean that provides access to the specified MongoDB database.
     * The database name used is "mls_note".
     *
     * @param mongoClient the {@link MongoClient} used to access the database
     * @return a {@link MongoDatabase} instance for accessing the "mls_note" database
     */
    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase("mls_note");
    }
}
