package ru.trainee.creditmanager.dto.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

/**
 * DTO for {@link ru.trainee.creditmanager.entity.Bank}
 */
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"id", "name", "customers", "loanOffers"})
public class BankResponseDetailDTO {
    Long id;
    String name;
    @JsonProperty("customers")
    Integer customersCost;
    @JsonProperty("credit_types")
    Integer creditTypesCost;
    @JsonProperty("loan_offers")
    Integer loanOffersCost;
}
