package ru.trainee.creditmanager.controller.rest.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.service.LoanOfferService;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/offers")
@Tag(name = "Кредитные предложения", description = "Операции над кредитными предложениями")
public class LoanOfferControllerV1 {

    private final LoanOfferService loanOfferService;

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
        return loanOfferService.create(dto);
    }

    @GetMapping("{id}/accept")
    @Operation(
            summary = "Принятие кредитного предложения",
            description = """
                    Принятие кредитного предложения. Присвоение статуса "accepted".
                    """
    )
    public void accepting(@PathVariable Long id){
        loanOfferService.accepting(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление кредитного предложения",
            description = """
                    Удаление кредитного предложения и каскадное удаление всех дочерних сущностей.
                    """
    )
    public void deleteBankById(@PathVariable Long id){
        loanOfferService.deleteById(id);
    }

}