package com.medilabo_solutions.note;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class MongoDBConnection {

    public static void main(String[] args) {
        String connectionString = "mongodb+srv://alicepogam59:87mJZW5blQb8YzUm@medilabonote.you6h.mongodb.net/?retryWrites=true&w=majority&appName=MedilaboNote";

        // Configuration du client MongoDB
        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(connectionString))
                        .serverApi(ServerApi.builder()
                                .version(ServerApiVersion.V1)
                                .build())
                        .build());

        // Accès à la base de données
        MongoDatabase database = mongoClient.getDatabase("mls_note");

        // Accès à la collection "note"
        MongoCollection<Document> collection = database.getCollection("note");

        // Récupération et affichage des documents
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }

        // Fermeture du client
        mongoClient.close();
    }
}
