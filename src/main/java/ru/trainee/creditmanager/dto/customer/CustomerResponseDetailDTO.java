package ru.trainee.creditmanager.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;

import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link ru.trainee.creditmanager.entity.Customer}
 */
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "firstname", "lastname", "surname", "series", "number", "email", "isActive", "loanOffers"})
public class CustomerResponseDetailDTO {
    private UUID id;

    private String firstname;

    private String lastname;

    private String surname;

    private Long series;

    private Long number;

    private String email;

    private boolean isActive;

    List<LoanOfferResponseShortDTO> loanOffers;
}
