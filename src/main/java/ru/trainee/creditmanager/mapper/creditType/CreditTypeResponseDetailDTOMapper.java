package ru.trainee.creditmanager.mapper.creditType;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.entity.CreditType;

import java.util.function.Function;

@Service
public class CreditTypeResponseDetailDTOMapper implements Function<CreditType, CreditTypeResponseDetailDTO> {

    @Override
    public CreditTypeResponseDetailDTO apply(CreditType creditType) {
        return new CreditTypeResponseDetailDTO(
                creditType.getId(),
                creditType.getName(),
                creditType.getCreditLimit(),
                creditType.getInterestRate(),
                creditType.getBank().getName()
        );
    }
}
