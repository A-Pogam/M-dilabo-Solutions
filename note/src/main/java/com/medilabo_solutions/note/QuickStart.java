package com.medilabo_solutions.note;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Arrays;

public class QuickStart {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://alicepogam59:87mJZW5blQb8YzUm@medilabonote.you6h.mongodb.net/?retryWrites=true&w=majority&appName=MedilaboNote";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            // Ping the MongoDB server to check the connection
            MongoDatabase database = mongoClient.getDatabase("mls_note");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

            // Get the collection
            MongoCollection<Document> collection = database.getCollection("note");

            // Create documents to insert
            Document note1 = new Document("patId", 1)
                    .append("patientName", "TestNone")
                    .append("notes", Arrays.asList("Le patient déclare qu'il se sent très bien", "Poids égal ou inférieur au poids recommandé"));

            Document note2 = new Document("patId", 2)
                    .append("patientName", "TestBorderline")
                    .append("notes", Arrays.asList("Le patient déclare qu'il ressent beaucoup de stress au travail", "Il se plaint également que son audition est anormale dernièrement"));

            Document note3 = new Document("patId", 2)
                    .append("patientName", "TestBorderline")
                    .append("notes", Arrays.asList("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois", "Il remarque également que son audition continue d'être anormale"));

            Document note4 = new Document("patId", 3)
                    .append("patientName", "TestInDanger")
                    .append("notes", Arrays.asList("Le patient déclare qu'il fume depuis peu"));

            Document note5 = new Document("patId", 3)
                    .append("patientName", "TestInDanger")
                    .append("notes", Arrays.asList("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière", "Il se plaint également de crises d’apnée respiratoire anormales", "Tests de laboratoire indiquant un taux de cholestérol LDL élevé"));

            Document note6 = new Document("patId", 4)
                    .append("patientName", "TestEarlyOnset")
                    .append("notes", Arrays.asList("Le patient déclare qu'il lui est devenu difficile de monter les escaliers", "Il se plaint également d’être essoufflé", "Tests de laboratoire indiquant que les anticorps sont élevés", "Réaction aux médicaments"));

            Document note7 = new Document("patId", 4)
                    .append("patientName", "TestEarlyOnset")
                    .append("notes", Arrays.asList("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"));

            Document note8 = new Document("patId", 4)
                    .append("patientName", "TestEarlyOnset")
                    .append("notes", Arrays.asList("Le patient déclare avoir commencé à fumer depuis peu", "Hémoglobine A1C supérieure au niveau recommandé"));

            Document note9 = new Document("patId", 4)
                    .append("patientName", "TestEarlyOnset")
                    .append("notes", Arrays.asList("Taille", "Poids", "Cholestérol", "Vertige", "Réaction"));

            // Insert documents into the collection
            collection.insertMany(Arrays.asList(note1, note2, note3, note4, note5, note6, note7, note8, note9));

            System.out.println("Documents inserted successfully!");
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
