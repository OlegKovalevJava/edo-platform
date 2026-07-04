package com.edoplatform.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Базовый интерфейс для всех доменных событий.
 */
public interface DomainEvent {

    UUID eventId();
    Instant occurredAt();
}
