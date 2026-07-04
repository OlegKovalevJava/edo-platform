package com.edoplatform.domain.event.document;

import com.edoplatform.domain.event.DomainEvent;
import com.edoplatform.domain.model.DocumentId;

import java.time.Instant;
import java.util.UUID;

/**
 * Событие, возникающее при создании нового документа.
 */
public record DocumentCreatedEvent(UUID eventId, DocumentId documentId, Instant occurredAt) implements DomainEvent {

    public DocumentCreatedEvent(DocumentId documentId) {
        this(UUID.randomUUID(), documentId, Instant.now());
    }
}
