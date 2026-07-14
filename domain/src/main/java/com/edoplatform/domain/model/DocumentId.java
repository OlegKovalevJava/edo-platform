package com.edoplatform.domain.model;

import com.edoplatform.domain.exception.InvalidDocumentIdException;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object, представляющий уникальный идентификатор документа.
 * Иммутабельный. Гарантирует валидность ID при создании.
 */
public record DocumentId(UUID value) {

    public DocumentId {
        Objects.requireNonNull(value, "Document ID value cannot be null");
    }

    /**
     * Генерирует новый случайный идентификатор.
     */
    public static DocumentId generate() {
        return new DocumentId(UUID.randomUUID());
    }

    /**
     * Создает DocumentId из строкового представления UUID.
     *
     * @param value строковое представление UUID (например, "550e8400-e29b-41d4-a716-446655440000")
     * @throws InvalidDocumentIdException если строка не является валидным UUID
     */
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