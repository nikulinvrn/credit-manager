package ru.trainee.creditmanager.service;

import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;

import java.util.UUID;

public interface LoanOfferService {

    LoanOfferResponseDetailDTO create(LoanOfferCreateDTO dto);

    LoanOfferResponseDetailDTO findById(UUID id);


    void deleteById(UUID id);

    void accepting(UUID id);

//    List<LoanOffer> findAllByCustomerId(Long customerId);
//    LoanOffer activating(Long id, boolean status);
//    LoanOffer deactivating(Long id);

}
