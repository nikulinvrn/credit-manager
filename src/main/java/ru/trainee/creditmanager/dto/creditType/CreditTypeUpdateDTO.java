package ru.trainee.creditmanager.dto.creditType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CreditTypeUpdateDTO {
    Long id;
    String name;
    @JsonProperty("credit_limit")
    Integer creditLimit;
    @JsonProperty("interest_rate")
    Double interestRate;
    @JsonProperty("bank_name")
    String bankName;
}
