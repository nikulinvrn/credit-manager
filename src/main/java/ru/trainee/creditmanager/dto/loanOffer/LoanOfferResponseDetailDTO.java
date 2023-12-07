package ru.trainee.creditmanager.dto.loanOffer;

import lombok.*;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.payment.PaymentResponseShortDTO;
import ru.trainee.creditmanager.entity.Payment;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanOfferResponseDetailDTO {

    Long id;
    CustomerResponseShortDTO customer;
    BankResponseShortDTO bank;
    CreditTypeResponseShortDTO creditType;
    List<PaymentResponseShortDTO> paymentSchedule; // Сделать краткое представление платежа
    Integer principalOfCredit;
    Integer sumOfInterest;
    Integer sumOfCredit;
    boolean isActive;
    boolean isAccepted;

}
