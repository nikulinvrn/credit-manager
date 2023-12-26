package ru.trainee.creditmanager.dto.loanOffer;

public record LoanOfferCreateDTO(Long passportSeries,
                                 Long passportNumber,
                                 String bankName,
                                 String creditTypeName,
                                 Integer loanTerm,
                                 Integer amountRequested) {
}
