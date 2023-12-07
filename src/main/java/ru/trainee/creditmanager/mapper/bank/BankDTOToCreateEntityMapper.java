package ru.trainee.creditmanager.mapper.bank;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.bank.BankCreateDTO;
import ru.trainee.creditmanager.entity.Bank;

import java.util.function.Function;

@Service
public class BankDTOToCreateEntityMapper implements Function<BankCreateDTO, Bank> {

    @Override
    public Bank apply(BankCreateDTO dto) {
        return new Bank(
                null,
                dto.getName(),
                null,
                null,
                null
        );
    }
}
