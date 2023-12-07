package ru.trainee.creditmanager.mapper.loanOffer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.mapper.bank.BankResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeResponseShortDTOMapper;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class LoanOfferResponseShortDTOMapper implements Function<LoanOffer, LoanOfferResponseShortDTO> {

    private final BankResponseShortDTOMapper bankResponseShortDTOMapper;
    private final CreditTypeResponseShortDTOMapper creditTypeResponseShortDTOMapper;


    @Override
    public LoanOfferResponseShortDTO apply(LoanOffer loanOffer) {
        return new LoanOfferResponseShortDTO(
                loanOffer.getId(),
                bankResponseShortDTOMapper.apply(loanOffer.getBank()),
                creditTypeResponseShortDTOMapper.apply(loanOffer.getCreditType()),
                loanOffer.getSumOfCredit(),
                loanOffer.isActive(),
                loanOffer.isAccepted()
        );
    }
}

