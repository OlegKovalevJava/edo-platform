package com.edoplatform.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Базовый интерфейс для всех доменных событий.
 * Каждое событие представляет факт, уже произошедший в системе.
 */
public interface DomainEvent {

    UUID eventId();
    Instant occurredAt();
}
