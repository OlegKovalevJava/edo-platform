package com.edoplatform.domain.model;

import com.edoplatform.domain.event.document.DocumentCreatedEvent;

/**
 * Временный контейнер для возврата сущности и события.
 * Будет заменён на Event Bus в будущем.
 */
public record DocumentCreationResult(
        Document document,
        DocumentCreatedEvent event
) {
}
