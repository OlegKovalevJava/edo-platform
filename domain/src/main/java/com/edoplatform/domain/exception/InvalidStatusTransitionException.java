package com.edoplatform.domain.exception;

import com.edoplatform.domain.model.DocumentStatus;

/**
 * Возникает при попытке выполнить недопустимый переход между статусами документа.
 * Например, попытка подписать документ в статусе DRAFT, минуя отправку на рассмотрение.
 */
public class InvalidStatusTransitionException extends RuntimeException {

    private final DocumentStatus currentStatus;
    private final DocumentStatus targetStatus;

    public InvalidStatusTransitionException(DocumentStatus currentStatus, DocumentStatus targetStatus) {
        super(String.format("Invalid status transition from %s to %s", currentStatus, targetStatus));
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    public DocumentStatus getCurrentStatus() {
        return currentStatus;
    }

    public DocumentStatus getTargetStatus() {
        return targetStatus;
    }
}
