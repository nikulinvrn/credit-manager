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
import ru.trainee.creditmanager.mapper.customer.CustomerDTOToCreateEntityMapper;
import ru.trainee.creditmanager.mapper.customer.CustomerResponseDetailDTOMapper;
import ru.trainee.creditmanager.mapper.customer.CustomerResponseShortDTOMapper;
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
    private final CustomerResponseDetailDTOMapper customerResponseDetailDTOMapper;
    private final CustomerDTOToCreateEntityMapper customerDTOToCreateEntityMapper;
    private final CustomerResponseShortDTOMapper customerResponseShortDTOMapper;

    @Override
    public CustomerResponseDetailDTO create(@NotNull CustomerCreateDTO dto){
        if(!customerRepository.existsBySeriesAndNumber(dto.getSeries(), dto.getNumber())){
            Customer createdCustomer = customerRepository
                    .save(customerDTOToCreateEntityMapper.apply(dto));
            return customerResponseDetailDTOMapper.apply(createdCustomer);
        } else {
            return customerResponseDetailDTOMapper
                    .apply(customerRepository.findBySeriesAndNumber(dto.getSeries(), dto.getNumber()));
        }
    }

    @Override
    public List<CustomerResponseShortDTO> getAllCustomers(PageRequest pageRequest){
        return customerRepository.getAllCustomers(pageRequest).stream()
                .map(customerResponseShortDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponseShortDTO> getAllInactiveCustomers(PageRequest pageRequest){
        return customerRepository.getAllInactiveCustomers(pageRequest).stream()
                .map(customerResponseShortDTOMapper)
                .collect(Collectors.toList());
    }


    @Override
    public CustomerResponseDetailDTO getCustomerById(UUID id) {
        Optional<Customer> customerOptional = customerRepository.getCustomerById(id);
        if(customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            return customerResponseDetailDTOMapper.apply(customer);
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
        Optional<Customer> customerOptional = customerRepository.getCustomerById(customer.getId());
        if (customerOptional.isPresent()){
            Customer editingCustomer = customerOptional.get();

            if (Objects.nonNull(customer.getFirstname())) editingCustomer.setFirstname(customer.getFirstname());
            if (Objects.nonNull(customer.getLastname()))  editingCustomer.setLastname(customer.getLastname());
            if (Objects.nonNull(customer.getSurname()))   editingCustomer.setSurname(customer.getSurname());
            if (Objects.nonNull(customer.getSeries()))    editingCustomer.setSeries(customer.getSeries());
            if (Objects.nonNull(customer.getNumber()))    editingCustomer.setNumber(customer.getNumber());
            if (Objects.nonNull(customer.getEmail()))     editingCustomer.setEmail(customer.getEmail());

            return customerResponseDetailDTOMapper.apply(customerRepository.save(editingCustomer));
        } else {
            throw new EntityNotFoundException("Customer " + customer.getId() + " does not exist. Check id.");
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
