package ru.trainee.creditmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.trainee.creditmanager.entity.Bank;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.entity.LoanOffer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankRepository extends CommonRepository<Bank> {

    Optional<Bank> findByName(String name);

    @Query("select c from Customer c join c.banks b where b.id = :id")
    Page<Customer> getBankCustomersById(UUID id, Pageable p);


    @Query("select ct from CreditType ct join ct.bank b where b.id = :id")
    Page<CreditType> getBankCreditTypesById(UUID id, Pageable p);


    @Query("select lo from LoanOffer lo join lo.bank b where b.id = :id")
    Page<LoanOffer> getBankLoanOffersById(UUID id, Pageable p);

}