package ru.trainee.creditmanager.mapper.loanOffer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.*;
import ru.trainee.creditmanager.mapper.bank.BankMapper;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeMapper;
import ru.trainee.creditmanager.mapper.customer.CustomerMapper;
import ru.trainee.creditmanager.mapper.payment.PaymentMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LoanOfferMapper {

    private final BankMapper bankMapper;
    private final CreditTypeMapper creditTypeMapper;
    private final CustomerMapper customerMapper;
    private final PaymentMapper paymentMapper;

    public LoanOfferResponseShortDTO toLoanOfferShortDto(LoanOffer loanOffer) {
        return new LoanOfferResponseShortDTO(
                loanOffer.getId(),
                bankMapper.toBankShortDto(loanOffer.getBank()),
                creditTypeMapper.toCreditTypeShortDto(loanOffer.getCreditType()),
                loanOffer.getSumOfCredit(),
                loanOffer.isActive(),
                loanOffer.isAccepted()
        );
    }

    public LoanOfferResponseDetailDTO toLoanOfferDetailDto(LoanOffer loanOffer) {

        return new LoanOfferResponseDetailDTO(
                loanOffer.getId(),
                customerMapper.toCustomerShortDto(loanOffer.getCustomer()),
                bankMapper.toBankShortDto(loanOffer.getBank()),
                creditTypeMapper.toCreditTypeShortDto(loanOffer.getCreditType()),
                loanOffer.getPaymentSchedule().stream()
                        .map(paymentMapper::toPaymentShortDto)
                        .collect(Collectors.toList()),
                loanOffer.getPrincipalOfCredit(),
                loanOffer.getSumOfInterest(),
                loanOffer.getSumOfCredit(),
                loanOffer.isActive(),
                loanOffer.isAccepted()
        );
    }

    public LoanOffer toLoanOfferCreateEntityWithoutPayments(
                Customer customer,
                Bank bank,
                CreditType creditType){
        LoanOffer offer = new LoanOffer();
        offer.setCustomer(customer);
        offer.setBank(bank);
        offer.setCreditType(creditType);
        offer.setActive(true); // по умолчанию активно
        offer.setAccepted(false); // по умолчанию не принято

        return offer;
    }
}
