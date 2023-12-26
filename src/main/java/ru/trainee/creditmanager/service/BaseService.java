package ru.trainee.creditmanager.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.trainee.creditmanager.entity.BaseEntity;

import java.util.UUID;

public abstract class BaseService<
        E extends BaseEntity,
        R extends JpaRepository<E, UUID>>
        implements CommonService<E> {

    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    @Override
    public E create(E entity) {
        return repository.save(entity);
    }

    @Override
    public Page<E> readAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public E findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    public E update(E entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
