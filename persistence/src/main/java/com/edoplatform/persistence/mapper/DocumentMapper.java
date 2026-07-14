package com.edoplatform.persistence.mapper;

import com.edoplatform.domain.model.Document;
import com.edoplatform.domain.model.DocumentId;
import com.edoplatform.persistence.entity.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

/**
 * Маппер между доменной моделью {@link Document} и JPA-сущностью {@link DocumentEntity}.
 * <p>
 * Использует MapStruct с componentModel = "spring" для внедрения как Spring Bean.
 * Таможенная логика — DocumentId ↔ UUID — вынесена в default-методы.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "id", source = "id", qualifiedByName = "documentIdToUuid")
    @Mapping(target = "status", source = "status")
    DocumentEntity toEntity(Document document);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToDocumentId")
    @Mapping(target = "status", source = "status")
    Document toDomain(DocumentEntity entity);

    @Named("documentIdToUuid")
    default UUID documentIdToUuid(DocumentId documentId) {
        return documentId != null ? documentId.value() : null;
    }

    @Named("uuidToDocumentId")
    default DocumentId uuidToDocumentId(UUID uuid) {
        return uuid != null ? new DocumentId(uuid) : null;
    }
}
