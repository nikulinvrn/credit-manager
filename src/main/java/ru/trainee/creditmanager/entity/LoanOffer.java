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
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "loan_offers")
public class LoanOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "credit_type_id")
    private CreditType creditType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanOffer", orphanRemoval = true)
    private List<Payment> paymentSchedule = new ArrayList<>();

    @Column(name = "principal_of_credit")
    private BigDecimal principalOfCredit;

    @Column(name = "sum_of_interest")
    private BigDecimal sumOfInterest;

    @Column(name = "sum_of_credit")
    private BigDecimal sumOfCredit;

    @Column(name = "is_active")
    private boolean isActive;

    private boolean isAccepted;

}
