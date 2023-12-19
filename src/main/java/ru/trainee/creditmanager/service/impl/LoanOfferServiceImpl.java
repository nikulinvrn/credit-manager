package ru.trainee.creditmanager.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.entity.Payment;
import ru.trainee.creditmanager.mapper.loanOffer.LoanOfferResponseDetailDTOMapper;
import ru.trainee.creditmanager.repository.LoanOfferRepository;
import ru.trainee.creditmanager.repository.PaymentRepository;
import ru.trainee.creditmanager.service.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LoanOfferServiceImpl implements LoanOfferService {

    private final LoanOfferRepository loanOfferRepository;

    private final CustomerService customerService;
    private final BankService bankService;
    private final CreditTypeService creditTypeService;
    private final PaymentService paymentService;

    private final LoanOfferResponseDetailDTOMapper loanOfferResponseDetailDTOMapper;
    private final PaymentRepository paymentRepository;


    @Override
    public LoanOfferResponseDetailDTO create(LoanOfferCreateDTO dto) {

        LoanOffer offer = new LoanOffer();
        offer.setCustomer(customerService.findBySeriesAndNumber(
                dto.getPassportSeries(),
                dto.getPassportNumber()
        ));
        offer.setBank(bankService.getBankByName(dto.getBankName()));
        offer.setCreditType(creditTypeService.findByName(dto.getCreditTypeName()));
        offer.setActive(true); // по умолчанию активно
        offer.setAccepted(false); // по умолчанию не принято
        LoanOffer savedOffer = loanOfferRepository.save(offer);
//        savedOffer.setPaymentSchedule(paymentScheduleGenerate(savedOffer.getId(), dto, offer.getCreditType()));
        try {
            savedOffer.setPaymentSchedule(paymentScheduleGenerateBigDecimal(savedOffer.getId(), dto, offer.getCreditType()));
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO: удалить костыль с исключением
        }
        return loanOfferResponseDetailDTOMapper.apply(loanOfferRepository.findById(savedOffer.getId()).get());
    }




    private List<Payment> paymentScheduleGenerate(
            Long loanOfferId,
            LoanOfferCreateDTO dto,
            CreditType creditType
    ){
        double interestRatePerMonth = creditType.getInterestRate() / 100 / 12;
        double sumOfPayment = dto.getAmountRequested() * (interestRatePerMonth  +
                (interestRatePerMonth / ((Math.pow((1 + interestRatePerMonth), dto.getLoanTerm())) - 1)));
        double balanceOfDebt;
        if(dto.getAmountRequested() <= creditType.getCreditLimit()){
            balanceOfDebt = (double) dto.getAmountRequested();
        } else {
            balanceOfDebt = (double) creditType.getCreditLimit();
        } // TODO: реализовать ответ в случае превышения лимита по сумме

        List<Payment> paymentSchedule = new ArrayList<>();
        LoanOffer loanOffer = loanOfferRepository.findById(loanOfferId).get();

        for(int i = 0; i < dto.getLoanTerm(); i++){
            Payment payment = new Payment();
            payment.setLoanOffer(loanOffer);
            payment.setSumOfPayment(sumOfPayment);
            double interestOfPayment = balanceOfDebt * interestRatePerMonth;
            if (interestOfPayment < 0) {interestOfPayment = 0;}
            payment.setInterestOfPayment(interestOfPayment);
            payment.setPrincipalOfPayment(sumOfPayment - interestOfPayment);
            balanceOfDebt = balanceOfDebt - (sumOfPayment - interestOfPayment);
            payment.setBalanceOfDebt(balanceOfDebt);
            payment.setDate(LocalDate.now().plusMonths(+ 1));
            paymentSchedule.add(payment);
        }

        paymentRepository.saveAll(paymentSchedule);
        return paymentSchedule;
    }

    /**
     * Method generate payment schedule. It's use BigDecimal, and it's work ONLY with once currency.
     *
     * @param loanOfferId
     * @param dto
     * @param creditType
     * @return
     */
    private List<Payment> paymentScheduleGenerateBigDecimal(
            Long loanOfferId,
            LoanOfferCreateDTO dto,
            CreditType creditType
    ) throws Exception {
        
        BigDecimal interestRatePerMonth = new BigDecimal(creditType.getInterestRate() / 100 / 12);

        BigDecimal sumOfPayment = interestRatePerMonth.add(BigDecimal.valueOf(1))
                .pow(dto.getLoanTerm())
                .subtract(BigDecimal.valueOf(1));
        sumOfPayment = interestRatePerMonth.divide(sumOfPayment,2, RoundingMode.HALF_EVEN)
                .add(interestRatePerMonth)
                .multiply(BigDecimal.valueOf(dto.getAmountRequested())).setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal balanceOfDebt;
        if(dto.getAmountRequested() <= creditType.getCreditLimit()){
            balanceOfDebt = BigDecimal.valueOf(dto.getAmountRequested());
        } else {
            balanceOfDebt = BigDecimal.valueOf(creditType.getCreditLimit());
        } // TODO: реализовать ответ в случае превышения лимита по сумме

        List<Payment> paymentSchedule = new ArrayList<>();
        Optional<LoanOffer> loanOfferOpt = loanOfferRepository.findById(loanOfferId);
        LoanOffer loanOffer;

        if (loanOfferOpt.isPresent()){
            loanOffer = loanOfferOpt.get();
        } else {
            throw new Exception("Loan offer is NULL");
        }
        // TODO: реализовать  проверку на null через isEmpty или нечто подобное
        //       и убрать велосипед с дополнительной переменной и каким-то странным исключением

//        for(int i = 0; i < dto.getLoanTerm(); i++){
//            Payment payment = new Payment();
//            payment.setLoanOffer(loanOffer);
//            payment.setSumOfPayment(sumOfPayment.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
//
//            BigDecimal interestOfPayment = balanceOfDebt.multiply(BigDecimal.valueOf(interestRatePerMonth))
//                    .setScale(2, RoundingMode.HALF_EVEN);
//
//            payment.setInterestOfPayment(interestOfPayment.doubleValue());
//            payment.setPrincipalOfPayment(sumOfPayment.subtract(interestOfPayment).doubleValue());
//            balanceOfDebt = balanceOfDebt.subtract(sumOfPayment.subtract(interestOfPayment));
//            payment.setBalanceOfDebt(balanceOfDebt.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
//            // TODO: производить коррекцию копеек в последнем платеже
//            if (i == dto.getLoanTerm() - 1){
//                payment.setPrincipalOfPayment(payment.getPrincipalOfPayment() + payment.getBalanceOfDebt());
//                payment.setSumOfPayment(payment.getSumOfPayment() + payment.getBalanceOfDebt());
//                payment.setBalanceOfDebt(0.0);
//            }
//            payment.setDate(LocalDate.now().plusMonths(+ 1));
//            paymentSchedule.add(payment);


        for(int i = 0; i < dto.getLoanTerm(); i++){
            Payment payment = new Payment();
            payment.setLoanOffer(loanOffer);
            payment.setSumOfPayment(sumOfPayment.doubleValue());

            BigDecimal interestOfPayment = balanceOfDebt.multiply(interestRatePerMonth).setScale(2, RoundingMode.HALF_EVEN);

            payment.setInterestOfPayment(interestOfPayment.doubleValue());
            payment.setPrincipalOfPayment(sumOfPayment.subtract(interestOfPayment).doubleValue());
            balanceOfDebt = balanceOfDebt.subtract(sumOfPayment.subtract(interestOfPayment));
            payment.setBalanceOfDebt(balanceOfDebt.doubleValue());
            // TODO: производить коррекцию копеек в последнем платеже
            if (i == dto.getLoanTerm() - 1){
                payment.setPrincipalOfPayment(BigDecimal.valueOf(payment.getPrincipalOfPayment() + payment.getBalanceOfDebt())
                        .setScale(2, RoundingMode.HALF_EVEN)
                        .doubleValue());
                payment.setSumOfPayment(BigDecimal.valueOf(payment.getSumOfPayment() + payment.getBalanceOfDebt())
                        .setScale(2, RoundingMode.HALF_EVEN)
                        .doubleValue());
                payment.setBalanceOfDebt(0.0);
            }
            payment.setDate(LocalDate.now().plusMonths(+ 1));
            paymentSchedule.add(payment);
        }

        paymentRepository.saveAll(paymentSchedule);
        return paymentSchedule;
    }
}
