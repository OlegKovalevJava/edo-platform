package com.edoplatform.domain.model;

import com.edoplatform.domain.event.document.DocumentCreatedEvent;

import java.time.Instant;
import java.util.Objects;

/**
 * Корневая сущность (Aggregate Root) документа в системе ЭДО.
 */
public class Document {

    private final DocumentId id;
    private DocumentStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    private Document(DocumentId id) {
        this.id = Objects.requireNonNull(id, "Document ID cannot be null");
        this.status = DocumentStatus.DRAFT;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public static DocumentCreationResult create(DocumentId id) {
        Document document = new Document(id);
        DocumentCreatedEvent event = new DocumentCreatedEvent(id);
        return new DocumentCreationResult(document, event);
    }

    public void changeStatus(DocumentStatus newStatus) {
        Objects.requireNonNull(newStatus, "New status cannot be null");
        if (this.status == newStatus) {
            return;
        }
        this.status = newStatus;
        this.updatedAt = Instant.now();
    }

    public DocumentId getId() {
        return id;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
