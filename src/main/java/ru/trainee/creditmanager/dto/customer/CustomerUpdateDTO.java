package ru.trainee.creditmanager.dto.customer;

import lombok.Value;

import java.util.UUID;

@Value
public class CustomerUpdateDTO {

    UUID id;
    String firstname;
    String lastname;
    String surname;
    Long series;
    Long number;
    String email;
}
