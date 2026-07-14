package com.edoplatform.persistence.entity;

import com.edoplatform.domain.model.Document;
import com.edoplatform.domain.model.DocumentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DocumentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public static DocumentEntity from(Document document) {
        DocumentEntity entity = new DocumentEntity();
        entity.id = document.getId().value();
        entity.status = document.getStatus();
        entity.createdAt = document.getCreatedAt();
        entity.updatedAt = document.getUpdatedAt();
        return entity;
    }

    public Document toDomain() {
        throw new UnsupportedOperationException("Use DocumentMapper for full reconstruction with events");
    }

    public void updateFrom(Document document) {
        this.status = document.getStatus();
        this.updatedAt = document.getUpdatedAt();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
