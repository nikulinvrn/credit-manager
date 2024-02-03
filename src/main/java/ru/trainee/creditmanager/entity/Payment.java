package ru.trainee.creditmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private BigDecimal principalOfPayment;

    private BigDecimal interestOfPayment;

    private BigDecimal sumOfPayment;

    private BigDecimal balanceOfDebt;

    private boolean isPayed;

    @ManyToOne
    private LoanOffer loanOffer;
}
