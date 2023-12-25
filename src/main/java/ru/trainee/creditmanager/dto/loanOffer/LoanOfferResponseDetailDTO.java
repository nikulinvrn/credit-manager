package ru.trainee.creditmanager.dto.loanOffer;

import lombok.*;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.payment.PaymentResponseShortDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanOfferResponseDetailDTO {

    UUID id;

    CustomerResponseShortDTO customer;
    BankResponseShortDTO bank;
    CreditTypeResponseShortDTO creditType;
    List<PaymentResponseShortDTO> paymentSchedule; // Сделать краткое представление платежа
    BigDecimal principalOfCredit;
    BigDecimal sumOfInterest;
    BigDecimal sumOfCredit;
    boolean isActive;
    boolean isAccepted;

}
