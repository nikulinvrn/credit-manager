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
import ru.trainee.creditmanager.dto.customer.CustomerCreateDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerUpdateDTO;
import ru.trainee.creditmanager.service.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/clients")
@Tag(name = "Клиенты", description = "Операции над личными карточками клиентов")
public class CustomerControllerV1 {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание нового пользователя",
            description = """
                    Метод возвращает детализированное представление личной
                    карточки созданного пользователя. Новый пользователь по умолчанию является активным.
                    В случае, если в базе уже существует пользователь с представленными
                    серией и номером паспорта, то возвращается личная карточка существующего пользователя.
                    """
    )
    @ApiResponse(responseCode = "201", description = "Created", content =
            {@Content(mediaType = "application/json", schema =
            @Schema(implementation = CustomerResponseDetailDTO.class))})
    public CustomerResponseDetailDTO create(@RequestBody CustomerCreateDTO dto) {
        return customerService.create(dto);
    }



    @GetMapping
    @Operation(
            summary = "Получение списка всех клиентов",
            description = """
                          Список не содержит банков, услугами которых
                          пользуется клиент, и персональных кредитных предложений.
                          """
    )
    public List<CustomerResponseShortDTO> getAllCustomers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return customerService.getAllCustomers(PageRequest.of(page, size));
    }



    @GetMapping("/trash-box")
    @Operation(
            summary = "(Административное) Получение списка неактивных клиентов",
            description = "Может быть необходимо для дальнейшей активации карточки по id."
    )
    public List<CustomerResponseShortDTO> getAllInactiveCustomers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return customerService.getAllInactiveCustomers(PageRequest.of(page, size));
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Получение карточки клиента",
            description = """
                          Карточка клиента содержит основные персональные данные,
                          включая персональные кредитные предложения.
                          """
    )
    public CustomerResponseDetailDTO getCustomerById(@PathVariable UUID id) {

        return customerService.getCustomerById(id);
    }



    @PutMapping(consumes = {"application/json"})
    @Operation(
            summary = "Обновление карточки клиента",
            description = """
                          Обновление информации в личной карточке клиента.
                          Обязательным полем в теле запроса является id. Остальные
                          поля заполняются только если необходимо внести изменения.
                          """
    )
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDetailDTO update(@RequestBody CustomerUpdateDTO dto) {

        return customerService.update(dto);
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Деактивация карточки клиента",
            description = """
                          Карточка клиента не отображается в списке клиентов, но не удаляется из БД.
                          При необходимости её снова можно активировать.
                          """
    )
    public CustomerResponseDetailDTO deactivate(@PathVariable UUID id) {

        customerService.deactivate(id);
        return customerService.getCustomerById(id);
    }



    @GetMapping("/{id}/activate")
    @Operation(
            summary = "Активация карточки клиента",
            description = "Карточка клиента снова отображается в списке клиентов."
    )
    public CustomerResponseDetailDTO activate(@PathVariable UUID id) {
        customerService.activate(id);
        return customerService.getCustomerById(id);
    }

}