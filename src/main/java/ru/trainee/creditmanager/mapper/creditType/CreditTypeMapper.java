package ru.trainee.creditmanager.mapper.creditType;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.service.BankService;

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
                dto.getName(),
                dto.getCreditLimit(),
                dto.getInterestRate(),
                bankService.getBankByName(dto.getBankName()),
                null
        );
    }
}
