package ru.trainee.creditmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.trainee.creditmanager.entity.BaseEntity;

import java.util.UUID;

public interface CommonService<E extends BaseEntity> {
    E create(E entity);

    Page<E> readAll(Pageable pageable);

    E findById(UUID id);

    E update(E entity);

    void delete(UUID id);
}
