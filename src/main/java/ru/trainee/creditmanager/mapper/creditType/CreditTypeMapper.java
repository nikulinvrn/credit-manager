package ru.trainee.creditmanager.mapper.creditType;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeUpdateDTO;
import ru.trainee.creditmanager.entity.Bank;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.service.BankService;

import java.util.UUID;

@Service
public class CreditTypeMapper {

    private final BankService bankService;

    public CreditTypeMapper(@Lazy BankService bankService) {
        this.bankService = bankService;
    }


    public CreditTypeResponseShortDTO toCreditTypeShortDto(CreditType creditType) {
        return new CreditTypeResponseShortDTO(
                creditType.getId(),
                creditType.getName(),
                creditType.getBank().getName(),
                creditType.getInterestRate()
        );
    }

    public CreditTypeResponseDetailDTO toCreditTypeDetailDto(CreditType creditType) {
        return new CreditTypeResponseDetailDTO(
                creditType.getId(),
                creditType.getName(),
                creditType.getCreditLimit(),
                creditType.getInterestRate(),
                creditType.getBank().getName()
        );
    }

    public CreditType toCreditTypeEntity(CreditTypeCreateDTO dto) {

        return new CreditType(
                null,
                dto.name(),
                dto.creditLimit(),
                dto.interestRate(),
                bankService.findById(dto.bankId()),
                null
        );
    }

    public CreditType toCreditTypeEntity(CreditType creditType, CreditTypeUpdateDTO dto) {
        creditType.setName(dto.name());
        creditType.setCreditLimit(dto.creditLimit());
        creditType.setInterestRate(dto.interestRate());
        creditType.setBank(bankService.findById(dto.bankId()));

        return creditType;
    }
}
