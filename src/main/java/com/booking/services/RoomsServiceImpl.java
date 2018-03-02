package com.booking.services;

import com.booking.models.RoomCategory;
import com.booking.models.domain.RoomEntity;
import com.booking.repository.RoomsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomsServiceImpl extends AbstractService<RoomsRepository, RoomEntity> implements RoomsService {
    public RoomsServiceImpl(final RoomsRepository repository) {
        super(repository);
    }

    @Override
    public Page<RoomEntity> findByCategory(final RoomCategory category, final Pageable pageable) {
        if (category == null) {
            return getPerPage(pageable);
        }
        return repository.findAllByCategory(category, pageable);
    }
}
