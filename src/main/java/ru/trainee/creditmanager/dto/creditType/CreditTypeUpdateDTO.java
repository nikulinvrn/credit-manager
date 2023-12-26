package ru.trainee.creditmanager.dto.creditType;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CreditTypeUpdateDTO(UUID id,
                                  String name,
                                  @JsonProperty("credit_limit")
                                  Integer creditLimit,
                                  @JsonProperty("interest_rate")
                                  Double interestRate,
                                  @JsonProperty("bank_name")
                                  String bankName) {
}
