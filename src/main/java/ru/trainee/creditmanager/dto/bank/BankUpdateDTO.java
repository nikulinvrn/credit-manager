package ru.trainee.creditmanager.dto.bank;

import java.util.UUID;

public record BankUpdateDTO (UUID id,
                             String name
                             ) {}
