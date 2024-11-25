package com.example.ch11.service;

import com.example.ch11.entity.Document;
import com.example.ch11.repository.DocumentRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    // omitted constructor
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @PostAuthorize("hasPermission(returnObject, 'ROLE_admin')")
    public Document getDocument(String code) {
        return documentRepository.findDocument(code);
    }

    @PreAuthorize("hasPermission(#bindingVar, 'document', 'ROLE_admin')")
    public Document getDocumentV2(String bindingVar) {
        return documentRepository.findDocument(bindingVar);
    }
}
