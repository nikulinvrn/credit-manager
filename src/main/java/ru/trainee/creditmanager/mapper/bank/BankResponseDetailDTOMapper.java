package ru.trainee.creditmanager.mapper.bank;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.bank.BankResponseDetailDTO;
import ru.trainee.creditmanager.entity.Bank;

import java.util.function.Function;

@Service
public class BankResponseDetailDTOMapper implements Function<Bank, BankResponseDetailDTO> {
    @Override
    public BankResponseDetailDTO apply(Bank bank) {

        Integer customersCost = bank.getCustomers().size();
        Integer loanOffersCost = bank.getLoanOffers().size();
        Integer creditTypesCost = bank.getCreditTypes().size();

        return new BankResponseDetailDTO(
                bank.getId(),
                bank.getName(),
                customersCost,
                creditTypesCost,
                loanOffersCost
        );
    }
}
