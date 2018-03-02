package com.booking.services;

import com.booking.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;

@Slf4j
public abstract class AbstractService<T extends JpaRepository<E, Long>, E> {

    final T repository;
    private final Class<E> persistentClass;

    AbstractService(final T repository) {
        this.repository = repository;
        this.persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public E save(final E entity) {
        return repository.save(entity);
    }

    public E findById(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(persistentClass.getSimpleName(), id));
    }

    public void delete(final Long id) {
        repository.delete(findById(id));
    }

    public Page<E> getPerPage(final Pageable pageable) {
        return repository.findAll(pageable);
    }



}
