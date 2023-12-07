package ru.trainee.creditmanager.dto.loanOffer;

import lombok.Value;

@Value
public class LoanOfferCreateDTO {
    Long passportSeries;
    Long passportNumber;
    String bankName;
    String creditTypeName;
    Integer loanTerm; // срок кредитования в месяцах
    Integer amountRequested; // запрашиваемая сумма в рублях
}
