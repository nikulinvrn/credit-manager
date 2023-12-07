package ru.trainee.creditmanager.mapper.bank;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.entity.Bank;

import java.util.function.Function;

@Service
public class BankResponseShortDTOMapper implements Function<Bank, BankResponseShortDTO> {
    @Override
    public BankResponseShortDTO apply(Bank bank) {
        return new BankResponseShortDTO(
                bank.getId(),
                bank.getName()
        );
    }
}
