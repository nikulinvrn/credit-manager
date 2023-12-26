package ru.trainee.creditmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.repository.CreditTypeRepository;
import ru.trainee.creditmanager.service.BaseService;
import ru.trainee.creditmanager.service.CreditTypeService;

@Service
public class CreditTypeServiceImpl
        extends BaseService<CreditType, CreditTypeRepository>
        implements CreditTypeService {

    public CreditTypeServiceImpl(CreditTypeRepository repository) {
        super(repository);
    }


    public CreditType findByName(String name) {
        return super.repository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Credit type " + name + " not found")
        );
    }

}

