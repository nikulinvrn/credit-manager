package ru.trainee.creditmanager.service;

import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.entity.LoanOffer;

public interface LoanOfferService {

    LoanOfferResponseDetailDTO create(LoanOfferCreateDTO dto);

//    LoanOffer findById(Long id);
//    List<LoanOffer> findAllByCustomerId(Long customerId);
//    LoanOffer activating(Long id, boolean status);
//    LoanOffer accepting(Long id);
//    LoanOffer deactivating(Long id);

}
