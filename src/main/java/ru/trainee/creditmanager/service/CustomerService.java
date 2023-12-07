package ru.trainee.creditmanager.service;

import org.springframework.data.domain.PageRequest;
import ru.trainee.creditmanager.dto.customer.CustomerCreateDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerUpdateDTO;
import ru.trainee.creditmanager.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponseDetailDTO create(CustomerCreateDTO dto);
    List<CustomerResponseShortDTO> getAllCustomers(PageRequest pageRequest);
    List<CustomerResponseShortDTO> getAllInactiveCustomers(PageRequest pageRequest);
    CustomerResponseDetailDTO getCustomerById(UUID id);

    Customer findBySeriesAndNumber(Long series, Long number);
    CustomerResponseDetailDTO update(CustomerUpdateDTO customer);
    void deactivate(UUID id);
    void activate(UUID id);
}
