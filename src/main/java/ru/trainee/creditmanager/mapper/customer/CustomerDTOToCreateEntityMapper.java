package ru.trainee.creditmanager.mapper.customer;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.customer.CustomerCreateDTO;
import ru.trainee.creditmanager.entity.Customer;

import java.util.function.Function;

@Service
public class CustomerDTOToCreateEntityMapper implements Function<CustomerCreateDTO, Customer> {

    @Override
    public Customer apply(CustomerCreateDTO dto) {
        return new Customer(
                null,
                dto.getFirstname(),
                dto.getLastname(),
                dto.getSurname(),
                dto.getSeries(),
                dto.getNumber(),
                dto.getEmail(),
                true,
                null,
                null
        );
    }
}
