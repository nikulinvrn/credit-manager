package ru.trainee.creditmanager.service;

import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;

import java.util.UUID;

public interface LoanOfferService {

    LoanOfferResponseDetailDTO create(LoanOfferCreateDTO dto);

    LoanOfferResponseDetailDTO findById(UUID id);


    void deleteById(UUID id);

    void accepting(UUID id);

}
