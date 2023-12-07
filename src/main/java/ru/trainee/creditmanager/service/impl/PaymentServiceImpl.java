package ru.trainee.creditmanager.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.entity.Payment;
import ru.trainee.creditmanager.repository.PaymentRepository;
import ru.trainee.creditmanager.service.PaymentService;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
}
