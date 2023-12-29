package ru.trainee.creditmanager.mapper.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.customer.CustomerCreateDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerUpdateDTO;
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

    /**
     * @param dto объект типа CustomerCreateDTO
     * @return объект класса Customer со значением полей baks и loanOffers равным null
     */
    public Customer toCustomerEntity(CustomerCreateDTO dto) {

        return new Customer(
                null,
                dto.firstname(),
                dto.lastname(),
                dto.surname(),
                dto.series(),
                dto.number(),
                dto.email(),
                dto.isActive(),
                null,
                null);
    }


    /**
     * Метод для создания нового объекта типа Customer на основании CustomerUpdateDTO
     *
     * @param customer объект типа Customer, который будет изменен
     * @param dto      объект типа CustomerUpdateDto, на основании данных которого будет
     *                 изменяться объект customer
     * @return новый объект типа Customer
     */
    public Customer toCustomerEntity(Customer customer, CustomerUpdateDTO dto) {

        customer.setFirstname(dto.firstname());
        customer.setLastname(dto.lastname());
        customer.setSurname(dto.surname());
        customer.setEmail(dto.email());
        customer.setActive(dto.isActive());

        return customer;
    }
}

