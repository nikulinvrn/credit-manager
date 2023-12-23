package ru.trainee.creditmanager.mapper.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.customer.CustomerCreateDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.mapper.bank.BankMapper;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class CustomerMapper {

    private final BankMapper bankMapper;
    private final CreditTypeMapper creditTypeMapper;

    public CustomerResponseShortDTO toCustomerShortDto(Customer customer) {
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


    public CustomerResponseDetailDTO toCustomerDetailDto(Customer customer) {

        List<LoanOfferResponseShortDTO> listOffers = new ArrayList<>();

        if (Objects.nonNull(customer.getLoanOffers())) {
            listOffers = customer.getLoanOffers().stream()
                    .map(loanOffer -> new LoanOfferResponseShortDTO(
                            loanOffer.getId(),
                            bankMapper.toBankShortDto(loanOffer.getBank()),
                            creditTypeMapper.toCreditTypeShortDto(loanOffer.getCreditType()),
                            loanOffer.getSumOfCredit(),
                            loanOffer.isActive(),
                            loanOffer.isAccepted()
                    ))
                    .toList();
        }

        return new CustomerResponseDetailDTO(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getSurname(),
                customer.getSeries(),
                customer.getNumber(),
                customer.getEmail(),
                customer.isActive(),
                listOffers
        );
    }

    public Customer toCustomerEntity(CustomerCreateDTO dto) {
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
