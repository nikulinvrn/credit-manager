package ru.trainee.creditmanager.mapper.loanOffer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.entity.Payment;
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
        List<Payment> payments = loanOffer.getPaymentSchedule();
        payments.forEach(p -> p.setLoanOffer(null));

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
}
