package com.edoplatform.domain.model;

/**
 * Статусы жизненного цикла документа в системе ЭДО.
 */
public enum DocumentStatus {

    DRAFT,
    PENDING_REVIEW,
    SIGNED,
    REJECTED,
    ARCHIVED
}
