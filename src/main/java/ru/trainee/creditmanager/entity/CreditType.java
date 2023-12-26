package ru.trainee.creditmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_types")
public class CreditType  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "credit_limit", nullable = false)
    private Integer creditLimit;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @OneToMany(mappedBy = "creditType")
    private List<LoanOffer> loanOffer = new ArrayList<>();
}
