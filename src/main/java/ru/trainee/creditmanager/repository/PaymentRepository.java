package ru.trainee.creditmanager.repository;

import org.springframework.stereotype.Repository;
import ru.trainee.creditmanager.entity.Payment;

@Repository
public interface PaymentRepository extends CommonRepository<Payment> {
}