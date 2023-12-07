package ru.trainee.creditmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.trainee.creditmanager.entity.LoanOffer;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanOfferRepository extends JpaRepository<LoanOffer, Long> {

//    List<LoanOffer> findAllByCustomerId(UUID Id);
//    List<LoanOffer> findAllByCustomerIdAndIsActiveTrue(Long customerId);
}