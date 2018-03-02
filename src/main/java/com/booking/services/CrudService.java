package com.booking.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T> {
    T save(final T object);

    T findById(final Long id);

    void delete(final Long id);

    Page<T> getPerPage(final Pageable pageable);
}
