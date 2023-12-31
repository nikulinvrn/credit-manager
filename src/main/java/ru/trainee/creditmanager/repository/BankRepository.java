package ru.trainee.creditmanager.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.trainee.creditmanager.entity.Bank;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.entity.LoanOffer;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("select b from Bank b left join b.customers where b.id = :id")
    Optional<Bank> getBankById(Long id);
    Optional<Bank> findByName(String name);

    @Query("select c from Customer c join c.banks b where b.id = :id")
    List<Customer> getBankCustomersById(Long id, PageRequest pageRequest);

    @Query("select count(c) from Customer c join c.banks b where b.id = :id")
    Long countCustomersByBank(Long id);

    @Query("select ct from CreditType ct join ct.bank b where b.id = :id")
    List<CreditType> getBankCreditTypesById(Long id, PageRequest pageRequest);

    @Query("select count(ct) from CreditType ct join ct.bank b where b.id = :id")
    Long countCreditTypesByBank(Long id);

    @Query("select lo from LoanOffer lo join lo.bank b where b.id = :id")
    List<LoanOffer> getBankLoanOffersById(Long id, PageRequest pageRequest);

    @Query("select count(lo) from LoanOffer lo join lo.bank b where b.id = :id")
    Long countLoanOffersByBank(Long id);

    @Query("select b from Bank b")
    List<Bank> getAllBanks(PageRequest pageRequest);

}
