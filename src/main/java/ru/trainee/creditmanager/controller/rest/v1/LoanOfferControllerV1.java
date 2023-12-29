package ru.trainee.creditmanager.controller.rest.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.entity.LoanOffer;
import ru.trainee.creditmanager.mapper.loanOffer.LoanOfferMapper;
import ru.trainee.creditmanager.service.BankService;
import ru.trainee.creditmanager.service.CreditTypeService;
import ru.trainee.creditmanager.service.CustomerService;
import ru.trainee.creditmanager.service.LoanOfferService;

import java.util.Objects;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/offers")
@Tag(name = "Кредитные предложения", description = "Операции над кредитными предложениями")
public class LoanOfferControllerV1 {

    private final LoanOfferService loanOfferService;

    private final CustomerService customerService;
    private final BankService bankService;
    private final CreditTypeService creditTypeService;

    private final LoanOfferMapper loanOfferMapper;

    @PostMapping
    @Operation(
            summary = "Создание кредитного предложения",
            description = """
                    Генерация кредитного предложения с графиком платежей.
                    Статус "активное" присваивается автоматически.
                    Присвоение статуса "accepted" производится отдельной операцией
                    после получения согласия клиента.
                    """
    )
    public LoanOfferResponseDetailDTO create(@RequestBody LoanOfferCreateDTO dto) {
        return loanOfferMapper.toLoanOfferDetailDto(
                loanOfferService.create(
                        loanOfferMapper.toLoanOfferCreateEntityWithoutPayments(
                                customerService.findById(dto.customerId()),
                                bankService.findById(dto.bankId()),
                                creditTypeService.findById(dto.creditTypeId())),
                        dto.amountRequested(),
                        dto.loanTerm(),
                        creditTypeService.findById(dto.creditTypeId()))
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение деталей кредитного предложения",
            description = """
                          Карточка кредитного предложения содержит ...
                          """
    )
    public LoanOfferResponseDetailDTO findById(@PathVariable UUID id) {

        LoanOffer offer = loanOfferService.findById(id);
        if(Objects.nonNull(offer)){
            return loanOfferMapper.toLoanOfferDetailDto(offer);
        } else {
            throw new EntityNotFoundException("Loan offer id: " + id + " not found.");
        }
    }


    @GetMapping("{id}/accept")
    @Operation(
            summary = "Принятие кредитного предложения",
            description = """
                    Принятие кредитного предложения. Присвоение статуса "accepted".
                    """
    )
    public void accepting(@PathVariable UUID id){
        loanOfferService.accepting(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление кредитного предложения",
            description = """
                    Удаление кредитного предложения и каскадное удаление всех дочерних сущностей.
                    """
    )
    public void deleteBankById(@PathVariable UUID id){
        loanOfferService.deleteById(id);
    }
}