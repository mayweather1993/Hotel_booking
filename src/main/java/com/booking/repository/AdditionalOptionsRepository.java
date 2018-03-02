package com.booking.repository;

import com.booking.models.domain.AdditionalOptionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalOptionsRepository extends JpaRepository<AdditionalOptionsEntity, Long> {
}
