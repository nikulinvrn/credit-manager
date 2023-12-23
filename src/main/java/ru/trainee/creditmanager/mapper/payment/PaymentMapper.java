package ru.trainee.creditmanager.mapper.payment;

import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.payment.PaymentResponseShortDTO;
import ru.trainee.creditmanager.entity.Payment;


@Service
public class PaymentMapper {

    public PaymentResponseShortDTO toPaymentShortDto(Payment payment) {
        return new PaymentResponseShortDTO(
                payment.getDate(),
                payment.getPrincipalOfPayment(),
                payment.getInterestOfPayment(),
                payment.getSumOfPayment(),
                payment.getBalanceOfDebt()
        );
    }
}
