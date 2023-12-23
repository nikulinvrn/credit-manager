package ru.trainee.creditmanager.mapper.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.mapper.bank.BankResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeResponseShortDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerResponseDetailDTOMapper implements Function<Customer, CustomerResponseDetailDTO> {

    private final BankResponseShortDTOMapper bankResponseShortDTOMapper;
    private final CreditTypeResponseShortDTOMapper creditTypeResponseShortDTOMapper;

    @Override
    public CustomerResponseDetailDTO apply(Customer customer) {

        List<LoanOfferResponseShortDTO> listOffers = new ArrayList<>();

        if (Objects.nonNull(customer.getLoanOffers())) {
            listOffers = customer.getLoanOffers().stream()
                    .map(loanOffer -> new LoanOfferResponseShortDTO(
                            loanOffer.getId(),
                            bankResponseShortDTOMapper.apply(loanOffer.getBank()),
                            creditTypeResponseShortDTOMapper.apply(loanOffer.getCreditType()),
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
}