package ru.trainee.creditmanager.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerCreateDTO(String firstname,
                                String lastname,
                                String surname,
                                Long series,
                                Long number,
                                String email,
                                @JsonProperty("active")
                                Boolean isActive) {
}
