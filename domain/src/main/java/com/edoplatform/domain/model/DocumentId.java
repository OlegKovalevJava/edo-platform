package com.edoplatform.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value Object, представляющий уникальный идентификатор документа.
 * Иммутабельный, сериализуемый. Гарантирует валидность ID при создании.
 * Использует Record для иммутабельности и автогенерации equals/hashCode.
 */
public record DocumentId(UUID value) {

    public DocumentId {
        Objects.requireNonNull(value, "Document ID value cannot be null");
    }

    public static DocumentId generate() {
        return new DocumentId(UUID.randomUUID());
    }

    public static DocumentId fromString(String value) {
        Objects.requireNonNull(value, "String value cannot be null");
        try {
            return new DocumentId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new InvalidDocumentIdException(value, e);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
