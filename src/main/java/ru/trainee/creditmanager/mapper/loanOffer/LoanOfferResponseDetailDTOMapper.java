package ru.trainee.creditmanager.mapper.loanOffer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.entity.Payment;
import ru.trainee.creditmanager.mapper.bank.BankResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.customer.CustomerResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.payment.PaymentResponseShortDTOMapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LoanOfferResponseDetailDTOMapper implements Function<LoanOffer, LoanOfferResponseDetailDTO> {

    private final BankResponseShortDTOMapper bankResponseShortDTOMapper;
    private final CreditTypeResponseShortDTOMapper creditTypeResponseShortDTOMapper;
    private final CustomerResponseShortDTOMapper customerResponseShortDTOMapper;
    private final PaymentResponseShortDTOMapper paymentResponseShortDTOMapper;


    @Override
    public LoanOfferResponseDetailDTO apply(LoanOffer loanOffer) {
        List<Payment> payments = loanOffer.getPaymentSchedule();
        payments.forEach(p -> p.setLoanOffer(null));

        return new LoanOfferResponseDetailDTO(
                loanOffer.getId(),
                customerResponseShortDTOMapper.apply(loanOffer.getCustomer()),
                bankResponseShortDTOMapper.apply(loanOffer.getBank()),
                creditTypeResponseShortDTOMapper.apply(loanOffer.getCreditType()),
                loanOffer.getPaymentSchedule().stream()
                        .map(paymentResponseShortDTOMapper)
                        .collect(Collectors.toList()),
                loanOffer.getPrincipalOfCredit(),
                loanOffer.getSumOfInterest(),
                loanOffer.getSumOfCredit(),
                loanOffer.isActive(),
                loanOffer.isAccepted()
        );
    }
}