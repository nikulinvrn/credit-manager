package ru.trainee.creditmanager.dto.customer;

public record CustomerCreateDTO(String firstname,
                                String lastname,
                                String surname,
                                Long series,
                                Long number,
                                String email) {
}
