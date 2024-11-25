package com.example.ch11.repository;


import com.example.ch11.entity.Document;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DocumentRepository {

    private Map<String, Document> documents =
            Map.of("abc123", new Document("natalie"),
                    "qwe123", new Document("natalie"),
                    "asd555", new Document("emma"));

    public Document findDocument(String var) {
        return documents.get(var);
    }
}

