package ru.trainee.creditmanager.dto.creditType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO for {@link ru.trainee.creditmanager.entity.CreditType}
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditTypeResponseDetailDTO {
    Long id;
    String name;
    @JsonProperty("credit_limit")
    Integer creditLimit;
    @JsonProperty("interest_rate")
    Double interestRate;
    @JsonProperty("bank_name")
    String bank;
}
