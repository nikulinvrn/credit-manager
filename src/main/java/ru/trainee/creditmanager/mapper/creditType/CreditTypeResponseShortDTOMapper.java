package ru.trainee.creditmanager.mapper.creditType;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.entity.CreditType;

import java.util.function.Function;

@Service
public class CreditTypeResponseShortDTOMapper  implements Function<CreditType, CreditTypeResponseShortDTO> {

    @Override
    public CreditTypeResponseShortDTO apply(CreditType creditType) {
        return new CreditTypeResponseShortDTO(
                creditType.getId(),
                creditType.getName(),
                creditType.getBank().getName(),
                creditType.getInterestRate()
        );
    }
}
