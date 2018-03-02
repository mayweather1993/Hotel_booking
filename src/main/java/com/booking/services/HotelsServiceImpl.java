package com.booking.services;

import com.booking.models.domain.HotelEntity;
import com.booking.repository.HotelsRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelsServiceImpl extends AbstractService<HotelsRepository, HotelEntity> implements HotelsService {
    public HotelsServiceImpl(final HotelsRepository repository) {
        super(repository);
    }
}
