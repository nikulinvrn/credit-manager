package ru.trainee.creditmanager.mapper.creditType;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.service.BankService;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class CreditTypeDTOToCreateEntityMapper implements Function<CreditTypeCreateDTO, CreditType> {

    private final BankService bankService;

    @Override
    public CreditType apply(CreditTypeCreateDTO dto) {
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
