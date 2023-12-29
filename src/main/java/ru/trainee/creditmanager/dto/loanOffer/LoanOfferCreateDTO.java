package ru.trainee.creditmanager.dto.loanOffer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record LoanOfferCreateDTO(
                                 @JsonProperty("customer_id")
                                 UUID customerId,
                                 @JsonProperty("bank_id")
                                 UUID bankId,
                                 @JsonProperty("credit_type_id")
                                 UUID creditTypeId,
                                 @JsonProperty("loan_term")
                                 Integer loanTerm,
                                 @JsonProperty("amount_requested")
                                 Integer amountRequested) {
}
