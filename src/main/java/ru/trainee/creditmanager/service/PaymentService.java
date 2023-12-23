package ru.trainee.creditmanager.service;

import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {

    Payment save(Payment payment);

    void saveAll(List<Payment> payments);

    /**
     * The payment is calculated as
     *     p = P * ((R(1+R)^N)/((1+R)^N-1))
     *
     * @param P the requested amount
     * @param R the interest rate
     * @param N number of periods
     * @return sum of monthly annuity payment amount
     */
    BigDecimal calculationOfAnnuityMonthlyPayment(BigDecimal P,
                                                  BigDecimal R,
                                                  Integer N);

    /**
     * @return detailed monthly payment
     */
    Payment calculatePayment(
            LoanOffer offer,
            BigDecimal interestRatePerMonth,
            BigDecimal sumOfPayment,
            int countOfPayment,
            BigDecimal balanceOfDebt,
            LocalDate date);
}
