package ru.trainee.creditmanager.service;

import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.LoanOffer;

import java.util.UUID;

public interface LoanOfferService extends CommonService<LoanOffer>{

    LoanOffer create(LoanOffer offer,
                     Integer amountRequested,
                     Integer loanTerm,
                     CreditType creditType);

    void deleteById(UUID id);

    void accepting(UUID id);

}
