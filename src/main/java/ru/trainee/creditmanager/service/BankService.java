package ru.trainee.creditmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.trainee.creditmanager.entity.Bank;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.entity.LoanOffer;

import java.util.UUID;

public interface BankService extends CommonService<Bank> {

    Bank getBankByName(String name);
    Page<Customer> getBankCustomersById(UUID id, Pageable p);
    Page<CreditType> getBankCreditTypesById(UUID id, Pageable p);
    Page<LoanOffer> getBankLoanOffersById(UUID id, Pageable p);
}