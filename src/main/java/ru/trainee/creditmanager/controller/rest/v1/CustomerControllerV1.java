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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.trainee.creditmanager.dto.PageableListResponseDTO;
import ru.trainee.creditmanager.dto.customer.CustomerCreateDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseDetailDTO;
import ru.trainee.creditmanager.dto.customer.CustomerResponseShortDTO;
import ru.trainee.creditmanager.dto.customer.CustomerUpdateDTO;
import ru.trainee.creditmanager.entity.Customer;
import ru.trainee.creditmanager.mapper.customer.CustomerMapper;
import ru.trainee.creditmanager.service.CustomerService;

import java.util.Objects;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/clients")
@Tag(name = "Клиенты", description = "Операции над личными карточками клиентов")
public class CustomerControllerV1 {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

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

        Customer customer = customerService.findBySeriesAndNumber(dto.series(), dto.number());
        if(Objects.isNull(customer)){
            return customerMapper.toCustomerDetailDto(
                    customerService.create(
                            customerMapper.toCustomerEntity(dto))
            );
        } else {
            throw new EntityExistsException("Customer already exixst.");
        }
    }



    @GetMapping
    @Operation(
            summary = "Получение списка всех клиентов",
            description = """
                          Список не содержит банков, услугами которых
                          пользуется клиент, и персональных кредитных предложений.
                          """
    )
    public PageableListResponseDTO<CustomerResponseShortDTO> readAll(Pageable p){

        Page<Customer> page = customerService.readAll(p);

        return new PageableListResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().stream()
                        .map(customerMapper::toCustomerShortDto)
                        .toList());
    }



    @GetMapping("/trash-box")
    @Operation(
            summary = "(Административное) Получение списка неактивных клиентов",
            description = "Может быть необходимо для дальнейшей активации карточки по id."
    )
    public PageableListResponseDTO<CustomerResponseShortDTO> getAllInactiveCustomers(Pageable p){

        Page<Customer> page = customerService.getAllInactiveCustomers(p);

        return new PageableListResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().stream()
                        .map(customerMapper::toCustomerShortDto)
                        .toList());
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
        Customer customer = customerService.findById(id);
        if(Objects.nonNull(customer)){
            return customerMapper.toCustomerDetailDto(customer);
        } else {
            throw new EntityNotFoundException("Custoemer " + id + " not found! Check id.");
        }
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
        return customerMapper.toCustomerDetailDto(
                customerService.update(
                        customerMapper.toCustomerEntity(customerService.findById(dto.id()), dto))
        );
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
        return customerMapper.toCustomerDetailDto(customerService.findById(id));
    }



    @GetMapping("/{id}/activate")
    @Operation(
            summary = "Активация карточки клиента",
            description = "Карточка клиента снова отображается в списке клиентов."
    )
    public CustomerResponseDetailDTO activate(@PathVariable UUID id) {
        customerService.activate(id);
        return customerMapper.toCustomerDetailDto(customerService.findById(id));
    }

}