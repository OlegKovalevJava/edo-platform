package com.edoplatform.domain.model;

import com.edoplatform.domain.event.DomainEvent;
import com.edoplatform.domain.event.document.DocumentCreatedEvent;
import com.edoplatform.domain.event.document.DocumentStatusChangedEvent;
import com.edoplatform.domain.exception.InvalidStatusTransitionException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Корневая сущность (Aggregate Root) документа в системе ЭДО.
 * Управляет жизненным циклом документа и генерирует доменные события.
 */
public class Document {

    private final DocumentId id;
    private DocumentStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private Document(DocumentId id) {
        this.id = Objects.requireNonNull(id, "Document ID cannot be null");
        this.status = DocumentStatus.DRAFT;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    private Document(DocumentId id, DocumentStatus status, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id, "Document ID cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt cannot be null");
    }

    public static Document create(DocumentId id) {
        Document document = new Document(id);
        document.registerEvent(new DocumentCreatedEvent(id));
        return document;
    }

    public static Document reconstruct(DocumentId id, DocumentStatus status, Instant createdAt, Instant updatedAt) {
        return new Document(id, status, createdAt, updatedAt);
    }

    public void changeStatus(DocumentStatus newStatus) {
        Objects.requireNonNull(newStatus, "New status cannot be null");

        if (this.status == newStatus) {
            return;
        }

        if (!this.status.canTransitionTo(newStatus)) {
            throw new InvalidStatusTransitionException(this.status, newStatus);
        }

        DocumentStatus oldStatus = this.status;
        this.status = newStatus;
        this.updatedAt = Instant.now();

        registerEvent(new DocumentStatusChangedEvent(id, oldStatus, newStatus));
    }

    private void registerEvent(DomainEvent event) {
        this.domainEvents.add(Objects.requireNonNull(event));
    }

    public List<DomainEvent> drainEvents() {
        if (domainEvents.isEmpty()) {
            return Collections.emptyList();
        }
        List<DomainEvent> events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
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
