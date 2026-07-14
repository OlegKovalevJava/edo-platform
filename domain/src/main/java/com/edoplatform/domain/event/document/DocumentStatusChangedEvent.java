package com.edoplatform.domain.event.document;

import com.edoplatform.domain.event.DomainEvent;
import com.edoplatform.domain.model.DocumentId;
import com.edoplatform.domain.model.DocumentStatus;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Событие, возникающее при изменении статуса документа.
 * Содержит как старый, так и новый статус для аудита и уведомлений.
 */
public record DocumentStatusChangedEvent(UUID eventId, DocumentId documentId, DocumentStatus oldStatus,
                                         DocumentStatus newStatus, Instant occurredAt) implements DomainEvent {

    public DocumentStatusChangedEvent(DocumentId documentId, DocumentStatus oldStatus, DocumentStatus newStatus) {
        this(UUID.randomUUID(), documentId, oldStatus, newStatus, Instant.now());
    }

    public DocumentStatusChangedEvent {
        Objects.requireNonNull(eventId, "eventId cannot be null");
        Objects.requireNonNull(documentId, "documentId cannot be null");
        Objects.requireNonNull(oldStatus, "oldStatus cannot be null");
        Objects.requireNonNull(newStatus, "newStatus cannot be null");
        Objects.requireNonNull(occurredAt, "occurredAt cannot be null");
    }
}
