package ru.trainee.creditmanager.mapper.payment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.payment.PaymentResponseShortDTO;
import ru.trainee.creditmanager.entity.Payment;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class PaymentResponseShortDTOMapper implements Function<Payment, PaymentResponseShortDTO> {

    @Override
    public PaymentResponseShortDTO apply(Payment payment) {
        return new PaymentResponseShortDTO(
                payment.getDate(),
                payment.getPrincipalOfPayment(),
                payment.getInterestOfPayment(),
                payment.getSumOfPayment(),
                payment.getBalanceOfDebt()
        );
    }
}
