package com.booking.services;

import com.booking.models.RoomCategory;
import com.booking.models.domain.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomsService extends CrudService<RoomEntity> {
    Page<RoomEntity> findByCategory(final RoomCategory category, final Pageable pageable);
}
