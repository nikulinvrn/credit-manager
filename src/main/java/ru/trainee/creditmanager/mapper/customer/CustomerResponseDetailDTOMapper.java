package ru.trainee.creditmanager.mapper.customer;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.Customer;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerResponseDetailDTOMapper implements Function<Customer, CustomerResponseDetailDTO> {
    @Override
    public CustomerResponseDetailDTO apply(Customer customer) {
        return new CustomerResponseDetailDTO(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getSurname(),
                customer.getSeries(),
                customer.getNumber(),
                customer.getEmail(),
                customer.isActive(),
                customer.getLoanOffers().stream()
                        .map(loanOffer -> new LoanOfferResponseShortDTO(
                                loanOffer.getId(),
                                null,
                                null,
                                loanOffer.getSumOfCredit(),
                                loanOffer.isActive(),
                                loanOffer.isAccepted()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
