package com.booking.services;

import com.booking.models.domain.AdditionalOptionsEntity;
import com.booking.repository.AdditionalOptionsRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomOptionsServiceImpl extends AbstractService<AdditionalOptionsRepository, AdditionalOptionsEntity> implements RoomOptionsService {
    public RoomOptionsServiceImpl(final AdditionalOptionsRepository repository) {
        super(repository);
    }
}
