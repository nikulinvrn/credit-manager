package ru.trainee.creditmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeUpdateDTO;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeMapper;
import ru.trainee.creditmanager.repository.CreditTypeRepository;
import ru.trainee.creditmanager.service.BankService;
import ru.trainee.creditmanager.service.CreditTypeService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditTypeServiceImpl implements CreditTypeService {

    private final CreditTypeRepository creditTypeRepository;

    private final CreditTypeMapper creditTypeMapper;

    private final BankService bankService;


    @Override
    public CreditTypeResponseDetailDTO create(@NotNull CreditTypeCreateDTO dto) {
        Optional<CreditType> ctOptional = creditTypeRepository.findByName(dto.getName());
        if (ctOptional.isEmpty()) {
            CreditType savedCreditType = creditTypeRepository.save(creditTypeMapper.toCreditTypeEntity(dto));
            return creditTypeMapper.toCreditTypeDetailDto(savedCreditType);
        } else {
            return creditTypeMapper.toCreditTypeDetailDto(ctOptional.get());
        }
    }

    @Override
    public List<CreditTypeResponseShortDTO> readAll(PageRequest pageRequest) {
        return creditTypeRepository.findAll(pageRequest).stream()
                .map(creditTypeMapper::toCreditTypeShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public CreditTypeResponseDetailDTO findById(UUID id) {
        Optional<CreditType> creditTypeOptional = creditTypeRepository.findById(id);
        if(creditTypeOptional.isPresent()) {
            CreditType creditType = creditTypeOptional.get();

            return creditTypeMapper.toCreditTypeDetailDto(creditType);
        } else {
            throw new EntityNotFoundException("CreditType not found. Check id.");
        }
    }

    @Override
    public CreditType findByName(String name) {
        Optional<CreditType> creditTypeOptional = creditTypeRepository.findByName(name);
        if(creditTypeOptional.isPresent()) {
            return creditTypeOptional.get();
        } else {
            throw new EntityNotFoundException("CreditType not found. Check id.");
        }
    }

    @Override
    public CreditTypeResponseDetailDTO update(CreditTypeUpdateDTO creditType) {
        Optional<CreditType> ctOptional = creditTypeRepository.findById(creditType.getId());
        if (ctOptional.isPresent()){
            CreditType editingCreditType = ctOptional.get();

            if (Objects.nonNull(creditType.getName())) {
                editingCreditType.setName(creditType.getName());
            }
            if (Objects.nonNull(creditType.getCreditLimit())) {
                editingCreditType.setCreditLimit(creditType.getCreditLimit());
            }
            if (Objects.nonNull(creditType.getInterestRate())) {
                editingCreditType.setInterestRate(creditType.getInterestRate());
            }
            if (Objects.nonNull(creditType.getBankName())) {
                editingCreditType.setBank(bankService.getBankByName(creditType.getBankName()));
            }


            return creditTypeMapper.toCreditTypeDetailDto(creditTypeRepository.save(editingCreditType));
        } else {
            throw new EntityNotFoundException("Credit type " + creditType.getId() + " does not exist. Check id.");
        }
    }

    @Override
    public void delete(UUID id) {
        creditTypeRepository.deleteById(id);
    }
}
