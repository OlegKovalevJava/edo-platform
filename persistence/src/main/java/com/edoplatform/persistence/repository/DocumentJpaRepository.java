package com.edoplatform.persistence.repository;

import com.edoplatform.persistence.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Внутренний JPA-репозиторий.
 * Не экспонируется наружу — используется только {@link DocumentRepositoryImpl}.
 */
interface DocumentJpaRepository extends JpaRepository<DocumentEntity, UUID> {
}
