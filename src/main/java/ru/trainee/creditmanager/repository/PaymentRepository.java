package ru.trainee.creditmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.trainee.creditmanager.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}