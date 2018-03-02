package com.booking.repository;

import com.booking.models.domain.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<BookingEntity, Long> {
}
