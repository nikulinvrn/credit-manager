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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        savedOffer.setPaymentSchedule(paymentScheduleGenerate(savedOffer.getId(), dto, offer.getCreditType()));
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
}
