package ru.trainee.creditmanager.mapper.customer;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.entity.Customer;

import java.util.function.Function;

@Service
public class CustomerResponseShortDTOMapper implements Function<Customer, CustomerResponseShortDTO> {
    @Override
    public CustomerResponseShortDTO apply(Customer customer) {
        return new CustomerResponseShortDTO(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getSurname(),
                customer.getSeries(),
                customer.getNumber(),
                customer.getEmail(),
                customer.isActive()
        );
    }
}
