package com.booking.repository;

import com.booking.models.domain.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelsRepository extends JpaRepository<HotelEntity, Long> {
}
