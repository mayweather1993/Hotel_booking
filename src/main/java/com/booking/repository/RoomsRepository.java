package com.booking.repository;

import com.booking.models.RoomCategory;
import com.booking.models.domain.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<RoomEntity, Long> {
    Page<RoomEntity> findAllByCategory(final RoomCategory category, final Pageable pageable);
}
