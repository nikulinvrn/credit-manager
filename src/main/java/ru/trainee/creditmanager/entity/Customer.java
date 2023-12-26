package ru.trainee.creditmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "customers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "surname")
    private String surname;

    @Column(name = "series", nullable = false)
    private Long series;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "email")
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private List<Bank> banks = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<LoanOffer> loanOffers = new ArrayList<>();
}
