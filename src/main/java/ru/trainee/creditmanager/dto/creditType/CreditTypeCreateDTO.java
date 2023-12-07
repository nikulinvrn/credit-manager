package ru.trainee.creditmanager.dto.creditType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreditTypeCreateDTO {

    String name;
    @JsonProperty("credit_limit")
    Integer creditLimit;
    @JsonProperty("interest_rate")
    Double interestRate;
    @JsonProperty("bank_name")
    String bankName;
}
