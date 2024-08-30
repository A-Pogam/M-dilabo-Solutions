package com.medilabo_solutions.note;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuickStart implements CommandLineRunner {

    private final MongoDatabase mongoDatabase;

    @Autowired
    public QuickStart(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public void run(String... args) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("note");

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
    }
}