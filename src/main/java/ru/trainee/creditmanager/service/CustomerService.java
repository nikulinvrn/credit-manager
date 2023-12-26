package ru.trainee.creditmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.trainee.creditmanager.entity.Customer;

import java.util.UUID;

public interface CustomerService extends CommonService<Customer>{

    Page<Customer> getAllInactiveCustomers(Pageable p);

    Customer findBySeriesAndNumber(Long series, Long number);
    void deactivate(UUID id);
    void activate(UUID id);
}
