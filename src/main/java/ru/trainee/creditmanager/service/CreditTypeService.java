package ru.trainee.creditmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeUpdateDTO;
import ru.trainee.creditmanager.entity.CreditType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditTypeService {
    CreditType create(CreditType creditType);

    Page<CreditType> readAll(Pageable pageable);

    Optional<CreditType> findById(UUID id);

    Optional<CreditType> findByName(String name);

    CreditType update(CreditType creditType);

    void delete(UUID id);
}