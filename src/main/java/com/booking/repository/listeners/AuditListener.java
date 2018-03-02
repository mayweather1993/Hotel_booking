package com.booking.repository.listeners;

import com.booking.models.Auditable;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Slf4j
public class AuditListener {

    @PrePersist
    public void prePersist(final Auditable entity) {
        log.debug("prePersist executed");
        final Instant now = Instant.now();
        entity.setCreatedDate(now);
        entity.setLastModifiedDate(now);
    }

    @PreUpdate
    public void preUpdate(final Auditable entity) {
        log.debug("preUpdate executed");
        final Instant now = Instant.now();
        entity.setLastModifiedDate(now);
    }
}
