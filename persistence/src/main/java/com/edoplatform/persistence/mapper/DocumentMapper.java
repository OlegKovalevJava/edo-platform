package com.edoplatform.persistence.mapper;

import com.edoplatform.domain.model.Document;
import com.edoplatform.domain.model.DocumentId;
import com.edoplatform.persistence.entity.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "id", source = "id", qualifiedByName = "documentIdToUuid")
    @Mapping(target = "status", source = "status")
    DocumentEntity toEntity(Document document);

    default Document toDomain(DocumentEntity entity) {
        if (entity == null) {
            return null;
        }
        return Document.reconstruct(
                uuidToDocumentId(entity.getId()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Named("documentIdToUuid")
    default UUID documentIdToUuid(DocumentId documentId) {
        return documentId != null ? documentId.value() : null;
    }

    @Named("uuidToDocumentId")
    default DocumentId uuidToDocumentId(UUID uuid) {
        return uuid != null ? new DocumentId(uuid) : null;
    }
}
