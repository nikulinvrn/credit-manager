package ru.trainee.creditmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.customer.CustomerCreateDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerUpdateDTO;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.mapper.customer.CustomerMapper;
import ru.trainee.creditmanager.repository.CustomerRepository;
import ru.trainee.creditmanager.service.CustomerService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDetailDTO create(@NotNull CustomerCreateDTO dto){

        if(customerRepository.existsBySeriesAndNumber(dto.series(), dto.number())){
            return customerMapper.toCustomerDetailDto(customerRepository
                    .findBySeriesAndNumber(dto.series(), dto.number()));
        } else {
            Customer createdCustomer = customerRepository
                    .save(customerMapper.toCustomerEntity(dto));
            return customerMapper.toCustomerDetailDto(createdCustomer);
        }
    }

    @Override
    public List<CustomerResponseShortDTO> getAllCustomers(PageRequest pageRequest){
        return customerRepository.getAllCustomers(pageRequest).stream()
                .map(customerMapper::toCustomerShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponseShortDTO> getAllInactiveCustomers(PageRequest pageRequest){
        return customerRepository.getAllInactiveCustomers(pageRequest).stream()
                .map(customerMapper::toCustomerShortDto)
                .collect(Collectors.toList());
    }


    @Override
    public CustomerResponseDetailDTO getCustomerById(UUID id) {
        Optional<Customer> customerOptional = customerRepository.getCustomerById(id);
        if(customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            return customerMapper.toCustomerDetailDto(customer);
        } else {
            throw new EntityNotFoundException("Customer not found. Check id.");
        }
    }

    @Override
    public Customer findBySeriesAndNumber(Long series, Long number) {
        return customerRepository.findBySeriesAndNumber(series, number);
    }

    @Override
    public CustomerResponseDetailDTO update(CustomerUpdateDTO customer){
        Optional<Customer> customerOptional = customerRepository.getCustomerById(customer.id());
        if (customerOptional.isPresent()){
            Customer editingCustomer = customerOptional.get();

            if (Objects.nonNull(customer.firstname())) editingCustomer.setFirstname(customer.firstname());
            if (Objects.nonNull(customer.lastname()))  editingCustomer.setLastname(customer.lastname());
            if (Objects.nonNull(customer.surname()))   editingCustomer.setSurname(customer.surname());
            if (Objects.nonNull(customer.series()))    editingCustomer.setSeries(customer.series());
            if (Objects.nonNull(customer.number()))    editingCustomer.setNumber(customer.number());
            if (Objects.nonNull(customer.email()))     editingCustomer.setEmail(customer.email());

            return customerMapper.toCustomerDetailDto(customerRepository.save(editingCustomer));
        } else {
            throw new EntityNotFoundException("Customer " + customer.id() + " does not exist. Check id.");
        }
    }

    @Override
    public void deactivate(UUID id) {
        customerRepository.deactivate(id);
    }

    @Override
    public void activate(UUID id) {
        customerRepository.activate(id);
    }
}
