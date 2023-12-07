package ru.trainee.creditmanager.dto.loanOffer;

import lombok.*;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanOfferResponseShortDTO {

    Long id;
    BankResponseShortDTO bank;
    CreditTypeResponseShortDTO creditType;
    Integer sumOfCredit;
    boolean isActive;
    boolean isAccepted;
}
