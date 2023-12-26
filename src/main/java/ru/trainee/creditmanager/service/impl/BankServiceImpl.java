package ru.trainee.creditmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.entity.Bank;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.repository.BankRepository;
import ru.trainee.creditmanager.service.BankService;
import ru.trainee.creditmanager.service.BaseService;

import java.util.Optional;
import java.util.UUID;

@Service
public class BankServiceImpl
    extends BaseService<Bank, BankRepository>
        implements BankService {


    public BankServiceImpl(BankRepository repository) {
        super(repository);
    }

    @Override
    public Bank getBankByName(String name) {
        Optional<Bank> bankOptional = repository.findByName(name);
        if(bankOptional.isPresent()){
            return bankOptional.get();
        } else {
            throw new EntityNotFoundException("Bank name: " + name + " does not exist!");
        }
    }

    @Override
    public Page<Customer> getBankCustomersById(UUID id, Pageable p) {
        return repository.getBankCustomersById(id, p);
    }

    @Override
    public Page<CreditType> getBankCreditTypesById(UUID id, Pageable p) {
        return repository.getBankCreditTypesById(id, p);
    }

    @Override
    public Page<LoanOffer> getBankLoanOffersById(UUID id, Pageable p) {
        return repository.getBankLoanOffersById(id, p);
    }
}
