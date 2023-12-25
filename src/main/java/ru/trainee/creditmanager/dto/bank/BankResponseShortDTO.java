package ru.trainee.creditmanager.dto.bank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.UUID;

/**
 * DTO for {@link ru.trainee.creditmanager.entity.Bank}
 */
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name"})
public class BankResponseShortDTO {
    UUID id;
    String name;
}
