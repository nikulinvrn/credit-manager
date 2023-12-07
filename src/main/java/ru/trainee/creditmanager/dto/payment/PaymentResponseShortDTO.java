package ru.trainee.creditmanager.dto.payment;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponseShortDTO {

    LocalDate date;
    Double principalOfPayment;
    Double interestOfPayment;
    Double sumOfPayment;
    Double balanceOfDebt;
}
