package ru.trainee.creditmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.entity.Payment;
import ru.trainee.creditmanager.exception.CreditLimitExceedException;
import ru.trainee.creditmanager.mapper.loanOffer.LoanOfferMapper;
import ru.trainee.creditmanager.repository.LoanOfferRepository;
import ru.trainee.creditmanager.service.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class LoanOfferServiceImpl implements LoanOfferService {

    private final LoanOfferRepository loanOfferRepository;

    private final CustomerService customerService;
    private final BankService bankService;
    private final CreditTypeService creditTypeService;
    private final PaymentService paymentService;

    private final LoanOfferMapper loanOfferMapper;

    @Override
    public LoanOfferResponseDetailDTO create(LoanOfferCreateDTO dto) {

        LoanOffer offer = new LoanOffer();
        offer.setCustomer(customerService.findBySeriesAndNumber(
                dto.passportSeries(),
                dto.passportNumber()
        ));
        offer.setBank(bankService.getBankByName(dto.bankName()));
        offer.setCreditType(creditTypeService.findByName(dto.creditTypeName()));
        offer.setActive(true); // по умолчанию активно
        offer.setAccepted(false); // по умолчанию не принято
        offer = loanOfferRepository.save(offer); // сохраняем, чтобы присвоить ID и вычитываем

        offer.setPaymentSchedule(paymentScheduleGenerate(offer.getId(),
                dto,
                offer.getCreditType()));

        return loanOfferMapper.toLoanOfferDetailDto(offer);
    }

    @Override
    public LoanOfferResponseDetailDTO findById(UUID id) {
        Optional<LoanOffer> loanOfferOptional = loanOfferRepository.findById(id);

        if(loanOfferOptional.isPresent()){
            LoanOffer loanOffer = loanOfferOptional.get();

            return loanOfferMapper.toLoanOfferDetailDto(loanOffer);
        } else {
            throw new EntityNotFoundException("The loan offer not found. Check id.");
        }
    }

    @Override
    public void accepting(UUID id){
        loanOfferRepository.accepting(id);
    }


    @Override
    public void deleteById(UUID id){
        loanOfferRepository.deleteById(id);
    }


    private List<Payment> paymentScheduleGenerate(
            UUID loanOfferId,
            LoanOfferCreateDTO dto,
            CreditType creditType
    ){

        if (dto.amountRequested() > creditType.getCreditLimit()){
            throw new CreditLimitExceedException(String.format(
                    "The credit limit is exceeded. Max value %d, but requested %d.",
                    creditType.getCreditLimit(),
                    dto.amountRequested()));
        }

        BigDecimal interestRatePerMonth = new BigDecimal(
                Math.round(creditType.getInterestRate() * 100) / 12
        ).divide(BigDecimal.valueOf(10000), 8, RoundingMode.HALF_UP);

        List<Payment> paymentSchedule = new ArrayList<>();

        Optional<LoanOffer> loanOfferOpt = loanOfferRepository.findById(loanOfferId);
        if (loanOfferOpt.isEmpty()) {
            throw new EntityNotFoundException("The payment schedule generation error: the loan offer is NULL.");
        }
        LoanOffer loanOffer = loanOfferOpt.get();

        BigDecimal sumInterestOfCredit = new BigDecimal("0");

        BigDecimal sumOfPayment = paymentService.calculationOfAnnuityMonthlyPayment(
                BigDecimal.valueOf(dto.amountRequested()),
                interestRatePerMonth,
                dto.loanTerm());

        LocalDate date = LocalDate.now();
        BigDecimal balanceOfDebt = BigDecimal.valueOf(dto.amountRequested());

        for(int i = 0; i < dto.loanTerm(); i++){
            date = date.plusMonths(+ 1);

            Payment payment = paymentService.calculatePayment(
                    loanOffer,
                    interestRatePerMonth,
                    sumOfPayment,
                    dto.loanTerm(),
                    balanceOfDebt,
                    date
            );

            balanceOfDebt = payment.getBalanceOfDebt();

            // The remaining amount of the debt that appeared during rounding is adjusted in the last payment
            if (i == dto.loanTerm() - 1){
                payment.setSumOfPayment(payment.getSumOfPayment().add(balanceOfDebt)
                        .setScale(2, RoundingMode.HALF_EVEN));
                payment.setBalanceOfDebt(new BigDecimal("0"));
            }

            paymentSchedule.add(payment);

            sumInterestOfCredit = sumInterestOfCredit.add(payment.getInterestOfPayment());
        }

        loanOffer.setSumOfCredit(sumOfPayment.multiply(BigDecimal.valueOf(dto.loanTerm()))
                .setScale(2, RoundingMode.HALF_EVEN));
        loanOffer.setPrincipalOfCredit(BigDecimal.valueOf(dto.amountRequested())
                .setScale(2, RoundingMode.HALF_EVEN));
        loanOffer.setSumOfInterest(sumInterestOfCredit
                .setScale(2, RoundingMode.HALF_EVEN));

        paymentService.saveAll(paymentSchedule);

        return paymentSchedule;
    }
}
