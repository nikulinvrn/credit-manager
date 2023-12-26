package ru.trainee.creditmanager.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.repository.CustomerRepository;
import ru.trainee.creditmanager.service.BaseService;
import ru.trainee.creditmanager.service.CustomerService;

import java.util.UUID;

@Service
public class CustomerServiceImpl
        extends BaseService<Customer, CustomerRepository>
        implements CustomerService {

    public CustomerServiceImpl(CustomerRepository repository){super(repository);}


    @Override
    public Page<Customer> readAll(Pageable p) { return repository.readAll(p); }

    @Override
    public Page<Customer> getAllInactiveCustomers(Pageable p) {
        return repository.getAllInactiveCustomers(p);
    }

    @Override
    public Customer findBySeriesAndNumber(Long series, Long number) {
        return repository.findBySeriesAndNumber(series, number);
    }


    @Override
    public void deactivate(UUID id) {
        repository.deactivate(id);
    }

    @Override
    public void activate(UUID id) {
        repository.activate(id);
    }
}