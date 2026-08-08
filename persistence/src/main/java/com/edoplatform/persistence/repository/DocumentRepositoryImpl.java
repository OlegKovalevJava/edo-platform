package com.edoplatform.persistence.repository;

import com.edoplatform.domain.model.Document;
import com.edoplatform.domain.model.DocumentId;
import com.edoplatform.persistence.entity.DocumentEntity;
import com.edoplatform.persistence.mapper.DocumentMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {

    private final DocumentJpaRepository jpaRepository;
    private final DocumentMapper mapper;

    public DocumentRepositoryImpl(DocumentJpaRepository jpaRepository, DocumentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Document> findById(DocumentId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    @Transactional
    public void save(Document document) {
        DocumentEntity entity = mapper.toEntity(document);
        jpaRepository.save(entity);
    }
}
