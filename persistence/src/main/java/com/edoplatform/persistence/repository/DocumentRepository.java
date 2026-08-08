package com.edoplatform.persistence.repository;

import com.edoplatform.domain.model.Document;
import com.edoplatform.domain.model.DocumentId;

import java.util.Optional;

/**
 * Репозиторий документов.
 * Работает с доменной моделью {@link Document}, скрывая JPA-детали.
 */
public interface DocumentRepository {

    Optional<Document> findById(DocumentId id);
    void save(Document document);
}
