package com.edoplatform.persistence.mapper;

import com.edoplatform.domain.model.Document;
import com.edoplatform.persistence.entity.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "id", expression = "java(document.getId().value())")
    @Mapping(target = "status", expression = "java(document.getStatus().name())")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    DocumentEntity toEntity(Document document);

    @Mapping(target = "id", expression = "java(new com.edoplatform.domain.model.DocumentId(entity.getId()))")
    @Mapping(target = "status", expression = "java(com.edoplatform.domain.model.DocumentStatus.valueOf(entity.getStatus()))")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    Document toDomain(DocumentEntity entity);
}
