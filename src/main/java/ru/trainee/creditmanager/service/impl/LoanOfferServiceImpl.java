package ru.trainee.creditmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.entity.Payment;
import ru.trainee.creditmanager.exception.CreditLimitExceedException;
import ru.trainee.creditmanager.repository.LoanOfferRepository;
import ru.trainee.creditmanager.service.BaseService;
import ru.trainee.creditmanager.service.LoanOfferService;
import ru.trainee.creditmanager.service.PaymentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class LoanOfferServiceImpl
        extends BaseService<LoanOffer, LoanOfferRepository>
        implements LoanOfferService {


    public LoanOfferServiceImpl(LoanOfferRepository repository,
                                PaymentService paymentService) {
        super(repository);
        this.paymentService = paymentService;
    }

    private final PaymentService paymentService;


    public LoanOffer create(LoanOffer offer,
                            Integer amountRequested,
                            Integer loanTerm,
                            CreditType creditType) {
        offer = repository.save(offer);

        paymentService.saveAll(
                paymentScheduleGenerate(offer.getId(),
                        amountRequested,
                        loanTerm,
                        creditType));

        return repository.findById(offer.getId()).orElseThrow(
                () -> new EntityNotFoundException("Loan offer not found after creating. " +
                        "Check full list offers.")
        );
    }

    @Override
    public void accepting(UUID id) {
        repository.accepting(id);
    }


    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }


    private List<Payment> paymentScheduleGenerate(
            UUID loanOfferId,
            Integer amountRequested,
            Integer loanTerm,
            CreditType creditType
    ) {
        if (amountRequested > creditType.getCreditLimit()) {
            throw new CreditLimitExceedException(String.format(
                    "The credit limit is exceeded. Max value %d, but requested %d.",
                    creditType.getCreditLimit(),
                    amountRequested));
        }

        BigDecimal interestRatePerMonth = new BigDecimal(
                Math.round(creditType.getInterestRate() * 100) / 12
        ).divide(BigDecimal.valueOf(10000), 8, RoundingMode.HALF_UP);

        List<Payment> paymentSchedule = new ArrayList<>();

        LoanOffer loanOffer = repository.findById(loanOfferId).orElseThrow(
                () -> new EntityNotFoundException(
                        "The payment schedule generation error: the loan offer is NULL."));

        BigDecimal sumInterestOfCredit = new BigDecimal("0");

        BigDecimal sumOfPayment = paymentService.calculationOfAnnuityMonthlyPayment(
                BigDecimal.valueOf(amountRequested),
                interestRatePerMonth,
                loanTerm);

        LocalDate date = LocalDate.now();
        BigDecimal balanceOfDebt = BigDecimal.valueOf(amountRequested);

        for (int i = 0; i < loanTerm; i++) {
            date = date.plusMonths(+1);

            Payment payment = paymentService.calculatePayment(
                    loanOffer,
                    interestRatePerMonth,
                    sumOfPayment,
                    loanTerm,
                    balanceOfDebt,
                    date
            );

            balanceOfDebt = payment.getBalanceOfDebt();

            // The remaining amount of the debt that appeared during
            // rounding is adjusted in the last payment
            if (i == loanTerm - 1) {
                payment.setSumOfPayment(payment.getSumOfPayment().add(balanceOfDebt)
                        .setScale(2, RoundingMode.HALF_EVEN));
                payment.setBalanceOfDebt(new BigDecimal("0"));
            }

            paymentSchedule.add(payment);

            sumInterestOfCredit = sumInterestOfCredit.add(payment.getInterestOfPayment());
        }


        // Establish general information about the loan offer
        loanOffer.setSumOfCredit(sumOfPayment.multiply(BigDecimal.valueOf(loanTerm))
                .setScale(2, RoundingMode.HALF_EVEN));
        loanOffer.setPrincipalOfCredit(BigDecimal.valueOf(amountRequested)
                .setScale(2, RoundingMode.HALF_EVEN));
        loanOffer.setSumOfInterest(sumInterestOfCredit
                .setScale(2, RoundingMode.HALF_EVEN));

        return paymentSchedule;
    }
}