package ru.trainee.creditmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "loan_offers")
public class LoanOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "credit_type_id")
    private CreditType creditType;

    @OneToMany(mappedBy = "loanOffer")
    private List<Payment> paymentSchedule = new ArrayList<>();

    private BigDecimal principalOfCredit;

    private BigDecimal sumOfInterest;

    private BigDecimal sumOfCredit;

    private boolean isActive;

    private boolean isAccepted;

}
