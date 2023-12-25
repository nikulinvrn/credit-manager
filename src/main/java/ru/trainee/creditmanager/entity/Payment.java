package ru.trainee.creditmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "principal_of_payment")
    private BigDecimal principalOfPayment;

    @Column(name = "interest_of_payment")
    private BigDecimal interestOfPayment;

    @Column(name = "sum_of_payment")
    private BigDecimal sumOfPayment;

    @Column(name = "balance_of_debt")
    private BigDecimal balanceOfDebt;

    @Column(name = "is_payed")
    private boolean isPayed;

    @ManyToOne
    private LoanOffer loanOffer;
}
