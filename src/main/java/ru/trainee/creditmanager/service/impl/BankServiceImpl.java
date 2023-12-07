package ru.trainee.creditmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.trainee.creditmanager.dto.PageableListResponseDTO;
import ru.trainee.creditmanager.dto.bank.BankCreateDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseDetailDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.entity.Bank;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.mapper.bank.BankDTOToCreateEntityMapper;
import ru.trainee.creditmanager.mapper.bank.BankResponseDetailDTOMapper;
import ru.trainee.creditmanager.mapper.bank.BankResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.customer.CustomerResponseShortDTOMapper;
import ru.trainee.creditmanager.mapper.loanOffer.LoanOfferResponseShortDTOMapper;
import ru.trainee.creditmanager.repository.BankRepository;
import ru.trainee.creditmanager.service.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankResponseDetailDTOMapper bankResponseDetailDTOMapper;
    private final BankResponseShortDTOMapper bankResponseShortDTOMapper;
    private final BankDTOToCreateEntityMapper bankDTOToCreateEntityMapper;
    private final CustomerResponseShortDTOMapper customerResponseShortDTOMapper;
    private final CreditTypeResponseShortDTOMapper creditTypeResponseShortDTOMapper;
    private final LoanOfferResponseShortDTOMapper loanOfferResponseShortDTOMapper;



    @Override
    public BankResponseDetailDTO create(@NotNull BankCreateDTO dto) {
        Optional<Bank> bankOptional = bankRepository.findByName(dto.getName());
        if (bankOptional.isEmpty()) {
            Bank savedBank = bankRepository.save(bankDTOToCreateEntityMapper.apply(dto));
            return bankResponseDetailDTOMapper.apply(savedBank);
        } else {
            return bankResponseDetailDTOMapper.apply(bankOptional.get());
        }
    }



    @Override
    public BankResponseDetailDTO getBankById(Long id) {
        Optional<Bank> bankOptional = bankRepository.getBankById(id);
        if(bankOptional.isPresent()){
            return bankResponseDetailDTOMapper.apply(bankOptional.get());
        } else {
            throw new EntityNotFoundException("Bank id: " + id + " does not exist!");
        }
    }

    @Override
    public Bank getBankByName(String name) {
        Optional<Bank> bankOptional = bankRepository.findByName(name);
        if(bankOptional.isPresent()){
            return bankOptional.get();
        } else {
            throw new EntityNotFoundException("Bank name: " + name + " does not exist!");
        }
    }



    @Override
    public PageableListResponseDTO<CustomerResponseShortDTO> getBankCustomersById(Long id, PageRequest pageRequest) {
        if (bankRepository.getBankById(id).isEmpty()) {
            throw new EntityNotFoundException("Bank id:" + id + " does not exist");
        }

        List<Customer> customers = bankRepository.getBankCustomersById(id, pageRequest);
        List<CustomerResponseShortDTO> customersResponse;
        if (Objects.nonNull(customers)) {
            customersResponse = customers.stream()
                    .map(customerResponseShortDTOMapper)
                    .toList();
        } else {
            customersResponse = new ArrayList<>();
        }

        return new PageableListResponseDTO<>(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                bankRepository.countCustomersByBank(id),
                customersResponse
        );
    }



    @Override
    public PageableListResponseDTO<CreditTypeResponseShortDTO> getBankCreditTypesById(Long id, PageRequest pageRequest) {
        if (bankRepository.getBankById(id).isEmpty()) {
            throw new EntityNotFoundException("Bank id:" + id + " does not exist");
        }

        List<CreditType> creditTypes = bankRepository.getBankCreditTypesById(id, pageRequest);
        List<CreditTypeResponseShortDTO> creditTypeResponse;
        if (Objects.nonNull(creditTypes)) {
            creditTypeResponse = creditTypes.stream()
                    .map(creditTypeResponseShortDTOMapper)
                    .toList();
        } else {
            creditTypeResponse = new ArrayList<>();
        }

        return new PageableListResponseDTO<>(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                bankRepository.countCreditTypesByBank(id),
                creditTypeResponse
        );
    }



    @Override
    public PageableListResponseDTO<LoanOfferResponseShortDTO> getBankLoanOffersById(Long id, PageRequest pageRequest) {
        if (bankRepository.getBankById(id).isEmpty()) {
            throw new EntityNotFoundException("Bank id:" + id + " does not exist");
        }

        List<LoanOffer> loanOffers = bankRepository.getBankLoanOffersById(id, pageRequest);
        List<LoanOfferResponseShortDTO> loanOffersResponse;
        if (Objects.nonNull(loanOffers)) {
            loanOffersResponse = loanOffers.stream()
                    .map(loanOfferResponseShortDTOMapper)
                    .toList();
        } else {
            loanOffersResponse = new ArrayList<>();
        }

        return new PageableListResponseDTO<>(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                bankRepository.countLoanOffersByBank(id),
                loanOffersResponse
        );
    }



    @Override
    public List<BankResponseShortDTO> getAllBanks(PageRequest pageRequest) {
        return bankRepository.getAllBanks(pageRequest).stream()
                .map(bankResponseShortDTOMapper)
                .collect(Collectors.toList());
    }



    @Override
    public void deleteById(Long id) {
        // Банк нельзя удалять, пока он содержит хотя бы одного активного клиента или активное кредитное предложение
        // ... но оставим это на потом (на фронте допилят, ага), а каскадное удаление разрешим на уровне БД =)

        bankRepository.deleteById(id);
    }

}
