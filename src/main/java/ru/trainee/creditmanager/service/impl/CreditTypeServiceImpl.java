package ru.trainee.creditmanager.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.repository.CreditTypeRepository;
import ru.trainee.creditmanager.service.CreditTypeService;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreditTypeServiceImpl implements CreditTypeService {

    private final CreditTypeRepository creditTypeRepository;

    @Override
    public CreditType create(CreditType creditType) {
        return creditTypeRepository.save(creditType);
    }

    @Override
    public Page<CreditType> readAll(Pageable pageable) {
        return creditTypeRepository.findAll(pageable);
    }

    @Override
    public Optional<CreditType> findById(UUID id) {
        return creditTypeRepository.findById(id);
    }

    @Override
    public Optional<CreditType> findByName(String name) {
        return creditTypeRepository.findByName(name);
    }

    @Override
    public CreditType update(CreditType creditType) {
        return creditTypeRepository.save(creditType);
    }

    @Override
    public void delete(UUID id) {
        creditTypeRepository.deleteById(id);
    }
}
