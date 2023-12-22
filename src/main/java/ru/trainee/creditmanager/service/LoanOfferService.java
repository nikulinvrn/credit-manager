package ru.trainee.creditmanager.service;

import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.entity.LoanOffer;

public interface LoanOfferService {

    LoanOfferResponseDetailDTO create(LoanOfferCreateDTO dto);

    LoanOfferResponseDetailDTO findById(Long id);


    void deleteById(Long id);

    void accepting(Long id);

//    List<LoanOffer> findAllByCustomerId(Long customerId);
//    LoanOffer activating(Long id, boolean status);
//    LoanOffer deactivating(Long id);

}
