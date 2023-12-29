package ru.trainee.creditmanager.dto.loanOffer;

import java.util.UUID;

public record LoanOfferCreateDTO(UUID customerId,
                                 UUID bankId,
                                 UUID creditTypeId,
                                 Integer loanTerm,
                                 Integer amountRequested) {
}
