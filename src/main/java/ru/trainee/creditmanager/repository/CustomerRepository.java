package ru.trainee.creditmanager.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trainee.creditmanager.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CommonRepository<Customer> {


    @Query("select c from Customer c where c.isActive = true")
    List<Customer> getAllCustomers(PageRequest pageRequest);

    @Query("select c from Customer c where c.isActive = false")
    List<Customer> getAllInactiveCustomers(PageRequest pageRequest);

    @Query("select c from Customer c where c.id = :id")
    Optional<Customer> getCustomerById(UUID id);

    Customer findBySeriesAndNumber(Long series, Long number);

    boolean existsBySeriesAndNumber(Long series, Long number);

    @Transactional
    @Modifying
    @Query("update Customer u set u.isActive = false where u.id = :id")
    void deactivate(UUID id);

    @Transactional
    @Modifying
    @Query("update Customer u set u.isActive = true where u.id = :id")
    void activate(UUID id);
}