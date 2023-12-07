package ru.trainee.creditmanager.dto.creditType;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * DTO for {@link ru.trainee.creditmanager.entity.CreditType}
 */
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditTypeResponseShortDTO {
    Long id;
    String name;
    String bank;
    Double interestRate;
}
