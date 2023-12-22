package ru.trainee.creditmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.entity.LoanOffer;

import java.util.Optional;

@Repository
public interface LoanOfferRepository extends JpaRepository<LoanOffer, Long> {

    @Query("select lo from LoanOffer lo where lo.id = :id")
    Optional<LoanOffer> findById(Long id);

    @Transactional
    @Modifying
    @Query("update LoanOffer lo set lo.isAccepted = true where lo.id = :id")
    void accepting(Long id);


//    List<LoanOffer> findAllByCustomerId(UUID Id);
//    List<LoanOffer> findAllByCustomerIdAndIsActiveTrue(Long customerId);
}