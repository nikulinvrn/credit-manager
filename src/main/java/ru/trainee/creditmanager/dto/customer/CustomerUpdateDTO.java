package ru.trainee.creditmanager.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CustomerUpdateDTO(UUID id,
                                String firstname,
                                String lastname,
                                String surname,
                                Long series,
                                Long number,
                                String email,
                                @JsonProperty("active")
                                Boolean isActive) {
}
