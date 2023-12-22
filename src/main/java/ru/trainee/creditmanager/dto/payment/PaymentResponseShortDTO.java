package ru.trainee.creditmanager.dto.payment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponseShortDTO {

    LocalDate date;
    BigDecimal principalOfPayment;
    BigDecimal interestOfPayment;
    BigDecimal sumOfPayment;
    BigDecimal balanceOfDebt;
}
