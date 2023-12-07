package ru.trainee.creditmanager.service;

import org.springframework.data.domain.PageRequest;
import ru.trainee.creditmanager.dto.PageableListResponseDTO;
import ru.trainee.creditmanager.dto.bank.BankCreateDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseDetailDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.Bank;

import java.util.List;

public interface BankService {

    BankResponseDetailDTO create(BankCreateDTO dto);
    BankResponseDetailDTO getBankById(Long id);

    Bank getBankByName(String name);
    PageableListResponseDTO<CustomerResponseShortDTO> getBankCustomersById(Long id, PageRequest pageRequest);
    PageableListResponseDTO<CreditTypeResponseShortDTO> getBankCreditTypesById(Long id, PageRequest pageRequest);
    PageableListResponseDTO<LoanOfferResponseShortDTO> getBankLoanOffersById(Long id, PageRequest pageRequest);
    List<BankResponseShortDTO> getAllBanks(PageRequest pageRequest);

    void deleteById(Long id);

}