package ru.trainee.creditmanager.controller.rest.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.trainee.creditmanager.dto.PageableListResponseDTO;
import ru.trainee.creditmanager.dto.bank.BankCreateDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseDetailDTO;
import ru.trainee.creditmanager.dto.bank.BankResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseShortDTO;
import ru.trainee.creditmanager.service.BankService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/banks")
@Tag(name = "Банки", description = "Операции над карточками банков")
public class BankControllerV1 {

    private final BankService bankService;

    @GetMapping("/{id}")
    public BankResponseDetailDTO getBankById(@PathVariable Long id) {
        return bankService.getBankById(id);
    }



    @GetMapping
    @Operation(
        summary = "Получение списка банков",
        description = "Список содержит только перечень идентификаторов и названий банков."
    )
    public List<BankResponseShortDTO> getAllBanks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return bankService.getAllBanks(PageRequest.of(page, size));
    }



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
        return bankService.create(dto);
    }


    @GetMapping("/{id}/customers")
    @Operation(
            summary = "Получение перечня клиентов банка",
            description = "Содержит краткие карточки клиентов"
    )
    public PageableListResponseDTO<CustomerResponseShortDTO> getBankCustomersById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return bankService.getBankCustomersById(id, PageRequest.of(page, size));
    }



    @GetMapping("/{id}/credit-types")
    @Operation(
            summary = "Получение перечня типов кредитов банка",
            description = "Содержит краткие карточки типов кредитов"
    )
    public PageableListResponseDTO<CreditTypeResponseShortDTO> getBankCreditTypesById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return bankService.getBankCreditTypesById(id, PageRequest.of(page, size));
    }



    @GetMapping("/{id}/loan-offers")
    @Operation(
            summary = "Получение перечня кредитных предложений банка",
            description = "Содержит краткие карточки кредитных предложений"
    )
    public PageableListResponseDTO<LoanOfferResponseShortDTO> getBankLoanOffersById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return bankService.getBankLoanOffersById(id, PageRequest.of(page, size));
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление банка",
            description = """
                    Удаление банка и каскадное удаление всех дочерних сущностей:
                    типы кредитов, кредитные предложения.
                    """
    )
    public void deleteBankById(@PathVariable Long id){
        bankService.deleteById(id);
    }
}