package ru.trainee.creditmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    //TODO: переделать сущность на BigDecimal?

    private Double principalOfPayment;

    private Double interestOfPayment;

    private Double sumOfPayment;

    private Double balanceOfDebt;

    private boolean isPayed;

    @ManyToOne
    private LoanOffer loanOffer;
}
