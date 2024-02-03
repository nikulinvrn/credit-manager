package ru.trainee.creditmanager.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.entity.Payment;
import ru.trainee.creditmanager.repository.PaymentRepository;
import ru.trainee.creditmanager.service.PaymentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void saveAll(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }


    @Override
    public BigDecimal calculationOfAnnuityMonthlyPayment(BigDecimal P,    // the requested amount
                                                          BigDecimal R,   // the interest rate
                                                          Integer N){     // number of periods
        BigDecimal firstStep = R.add(BigDecimal.valueOf(1)).pow(N);       // (1 + R)^N
        BigDecimal numerator = firstStep.multiply(R);
        BigDecimal denominator = firstStep.subtract(BigDecimal.valueOf(1));
        BigDecimal fraction = numerator.divide(denominator, 8, RoundingMode.HALF_EVEN);

        return fraction.multiply(P);
    }

    @Override
    public Payment calculatePayment(
            LoanOffer offer,
            BigDecimal interestRatePerMonth,
            BigDecimal sumOfPayment,
            int countOfPayment,
            BigDecimal balanceOfDebt,
            LocalDate date
            ){
        Payment payment = new Payment();
        payment.setLoanOffer(offer);

        BigDecimal interestOfPayment = balanceOfDebt.multiply(interestRatePerMonth)
                .setScale(2, RoundingMode.HALF_EVEN);

        payment.setSumOfPayment(sumOfPayment.setScale(2, RoundingMode.HALF_EVEN));
        payment.setInterestOfPayment(interestOfPayment.setScale(2, RoundingMode.HALF_EVEN));
        payment.setPrincipalOfPayment(sumOfPayment.subtract(interestOfPayment)
                .setScale(2, RoundingMode.HALF_EVEN));
        payment.setBalanceOfDebt(balanceOfDebt.subtract(payment.getSumOfPayment()
                        .subtract(payment.getInterestOfPayment()))
                .setScale(2, RoundingMode.HALF_EVEN));
        payment.setDate(date);

        return payment;
    }
}
