package ru.trainee.creditmanager.controller.rest.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
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
import ru.trainee.creditmanager.mapper.bank.BankMapper;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeMapper;
import ru.trainee.creditmanager.mapper.customer.CustomerMapper;
import ru.trainee.creditmanager.mapper.loanOffer.LoanOfferMapper;
import ru.trainee.creditmanager.service.BankService;

import java.util.Objects;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/banks")
@Tag(name = "Банки", description = "Операции над карточками банков")
public class BankControllerV1 {

    private final BankService bankService;

    private final BankMapper bankMapper;
    private final CustomerMapper customerMapper;
    private final CreditTypeMapper creditTypeMapper;
    private final LoanOfferMapper loanOfferMapper;



    @PostMapping
    @Operation(
            summary = "Создание нового банка",
            description = """
                    Метод возвращает детализированное представление
                    карточки созданного банка.
                    В случае, если в базе уже существует создаваемый банк
                    (проверка по имени), то возвращается карточка
                    существующего банка.
                    """
    )
    @ApiResponse(responseCode = "201", description = "Created", content =
            {@Content(mediaType = "application/json", schema =
            @Schema(implementation = BankResponseDetailDTO.class))})
    public BankResponseDetailDTO create(@RequestBody BankCreateDTO dto) {
        Bank bank = bankService.getBankByName(dto.getName());
        if(Objects.nonNull(bank)){
            return bankMapper.toBankDetailDto(
                    bankService.create(
                            bankMapper.toBankEntity(dto))
            );
        } else {
            throw new EntityExistsException("Credit type already exixst.");
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Просмотр карточки банка",
            description = "Просмотр информации о банке: клиенты, типы кредитов, кредитные предложения."
    )
    public BankResponseDetailDTO findById(@PathVariable UUID id) {
        Bank bank = bankService.findById(id);
        if(Objects.nonNull(bank)){
            return bankMapper.toBankDetailDto(bank);
        } else {
            throw new EntityNotFoundException("Bank " + id + " not found! Check id.");
        }
    }



    @GetMapping
    @Operation(
            summary = "Получение списка банков",
            description = "Список содержит только перечень идентификаторов и названий банков."
    )
    public PageableListResponseDTO<BankResponseShortDTO> readAll(Pageable p){

        Page<Bank> page = bankService.readAll(p);

        return new PageableListResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().stream()
                        .map(bankMapper::toBankShortDto)
                        .toList());
    }


    @GetMapping("/{id}/customers")
    @Operation(
            summary = "Получение перечня клиентов банка",
            description = "Содержит краткие карточки клиентов"
    )
    public PageableListResponseDTO<CustomerResponseShortDTO> getBankCustomersById(@PathVariable UUID id, Pageable p){

        Page<Customer> page = bankService.getBankCustomersById(id, p);

        return new PageableListResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().stream()
                        .map(customerMapper::toCustomerShortDto)
                        .toList());
    }



    @GetMapping("/{id}/credit-types")
    @Operation(
            summary = "Получение перечня типов кредитов банка",
            description = "Содержит краткие карточки типов кредитов"
    )
    public PageableListResponseDTO<CreditTypeResponseShortDTO> getBankCreditTypesById(@PathVariable UUID id, Pageable p){

        Page<CreditType> page = bankService.getBankCreditTypesById(id, p);

        return new PageableListResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().stream()
                        .map(creditTypeMapper::toCreditTypeShortDto)
                        .toList());
    }



    @GetMapping("/{id}/loan-offers")
    @Operation(
            summary = "Получение перечня кредитных предложений банка",
            description = "Содержит краткие карточки кредитных предложений"
    )
    public PageableListResponseDTO<LoanOfferResponseShortDTO> getBankLoanOffersById(@PathVariable UUID id, Pageable p){

        Page<LoanOffer> page = bankService.getBankLoanOffersById(id, p);

        return new PageableListResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().stream()
                        .map(loanOfferMapper::toLoanOfferShortDto)
                        .toList());
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление банка",
            description = """
                    Удаление банка и каскадное удаление всех дочерних сущностей:
                    типы кредитов, кредитные предложения.
                    """
    )
    public void deleteBankById(@PathVariable UUID id){
        bankService.delete(id);
    }
}