package ru.trainee.creditmanager.dto.customer;

import lombok.Value;

@Value
public class CustomerCreateDTO {

    String firstname;
    String lastname;
    String surname;
    Long series;
    Long number;
    String email;
}
