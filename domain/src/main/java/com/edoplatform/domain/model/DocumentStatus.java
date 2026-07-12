package com.edoplatform.domain.model;

import java.util.Collections;
import java.util.Set;

/**
 * Статусы жизненного цикла документа в системе ЭДО.
 * Каждый статус определяет разрешенные переходы.
 */
public enum DocumentStatus {

    DRAFT { // черновик
        @Override
        public Set<DocumentStatus> allowedTransitions() {
            return Set.of(PENDING_REVIEW, ARCHIVED);
        }
    },

    PENDING_REVIEW { // на рассмотрении
        @Override
        public Set<DocumentStatus> allowedTransitions() {
            return Set.of(SIGNED, REJECTED);
        }
    },

    SIGNED { // подписан
        @Override
        public Set<DocumentStatus> allowedTransitions() {
            return Set.of(ARCHIVED);
        }
    },

    REJECTED { // отклонен
        @Override
        public Set<DocumentStatus> allowedTransitions() {
            return Set.of(DRAFT);
        }
    },

    ARCHIVED { // архив
        @Override
        public Set<DocumentStatus> allowedTransitions() {
            return Collections.emptySet();
        }
    };

    public abstract Set<DocumentStatus> allowedTransitions();

    public boolean canTransitionTo(DocumentStatus targetStatus) {
        return allowedTransitions().contains(targetStatus);
    }
}
