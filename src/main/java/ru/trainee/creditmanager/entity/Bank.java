package ru.trainee.creditmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "banks")
public class Bank extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "customers_banks",
            joinColumns = @JoinColumn(name = "bank_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers;

    @OneToMany(mappedBy = "bank", orphanRemoval = true)
    private List<CreditType> creditTypes;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "bank", orphanRemoval = true)
    private List<LoanOffer> loanOffers;
}
