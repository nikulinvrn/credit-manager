package ru.trainee.creditmanager.dto.loanOffer;

import lombok.*;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanOfferResponseShortDTO {

    UUID id;

//    BankResponseShortDTO bank;
    BankResponseShortDTO bank;
    CreditTypeResponseShortDTO creditType;
    BigDecimal sumOfCredit;
    boolean isActive;
    boolean isAccepted;
}
