package ru.trainee.creditmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.trainee.creditmanager.entity.Customer;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CommonRepository<Customer> {

    @Query("select c from Customer c where c.isActive = true")
    Page<Customer> readAll(Pageable p);

    @Query("select c from Customer c where c.isActive = false")
    Page<Customer> getAllInactiveCustomers(Pageable p);

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