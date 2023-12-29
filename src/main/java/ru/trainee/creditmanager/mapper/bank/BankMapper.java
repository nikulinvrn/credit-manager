package ru.trainee.creditmanager.mapper.bank;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.bank.BankCreateDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseDetailDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.bank.BankUpdateDTO;
import ru.trainee.creditmanager.entity.Bank;

@Service
public class BankMapper {

    public BankResponseShortDTO toBankShortDto(Bank bank){
        return new BankResponseShortDTO(
                bank.getId(),
                bank.getName()
        );
    }

    public BankResponseDetailDTO toBankDetailDto(Bank bank) {

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


    public Bank toBankEntity(BankCreateDTO dto) {
        return new Bank(
                null,
                dto.getName(),
                null,
                null,
                null
        );
    }

    public Bank toBankEntity(Bank bank, BankUpdateDTO dto) {

        bank.setName(dto.name());

        return bank;
    }
}
