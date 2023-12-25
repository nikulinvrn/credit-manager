package ru.trainee.creditmanager.controller.rest.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeUpdateDTO;
import ru.trainee.creditmanager.service.CreditTypeService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/credit-types")
@Tag(name = "Типы кредитов", description = "Операции над карточками типов кредитов")
public class CreditTypeControllerV1 {

    private final CreditTypeService creditTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание нового типа кредитов",
            description = """
                    Метод возвращает детализированное представление карточки созданного типа.
                    В случае, если в базе уже существует тип кредита с указанным именем,
                    то возвращается карточка существующего типа.
                    """
    )
    @ApiResponse(responseCode = "201", description = "Created", content =
            {@Content(mediaType = "application/json", schema =
            @Schema(implementation = CreditTypeResponseDetailDTO.class))})
    public CreditTypeResponseDetailDTO create(@RequestBody CreditTypeCreateDTO dto) {
        return creditTypeService.create(dto);
    }


    @GetMapping
    @Operation(
            summary = "Получение списка всех типов кредитов",
            description = """
                    Список содержит краткие карточки типов кредитов.
                    """
    )
    public List<CreditTypeResponseShortDTO> readAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return creditTypeService.readAll(PageRequest.of(page, size));
    }




    @GetMapping("/{id}")
    @Operation(
            summary = "Получение карточки типа кредита",
            description = """
                              Карточка типа кредита содержит...
                              """
    )
    public CreditTypeResponseDetailDTO findById(@PathVariable UUID id) {

        return creditTypeService.findById(id);
    }



    @PutMapping(consumes = {"application/json"})
    @Operation(
            summary = "Обновление карточки типа кредита",
            description = """
                              Обновление информации в карточке типа.
                              Обязательным полем в теле запроса является id. Остальные
                              поля заполняются только если необходимо внести изменения.
                              """
    )
    @ResponseStatus(HttpStatus.OK)
    public CreditTypeResponseDetailDTO update(@RequestBody CreditTypeUpdateDTO creditType){
        return creditTypeService.update(creditType);
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление карточки типа кредита",
            description = """
                              Карточка просто удаляется...
                              """
    )
    public void delete(@PathVariable UUID id) {
        creditTypeService.delete(id);
    }
}
