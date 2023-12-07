package ru.trainee.creditmanager.service;

import org.springframework.data.domain.PageRequest;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeUpdateDTO;
import ru.trainee.creditmanager.entity.CreditType;

import java.util.List;

public interface CreditTypeService {
    CreditTypeResponseDetailDTO create(CreditTypeCreateDTO dto);

    List<CreditTypeResponseShortDTO> readAll(PageRequest pageRequest);

    CreditTypeResponseDetailDTO findById(Long id);

    CreditType findByName(String name);

    CreditTypeResponseDetailDTO update(CreditTypeUpdateDTO creditType);

    void delete(Long id);
}
