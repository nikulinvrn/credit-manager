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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.trainee.creditmanager.dto.PageableListResponseDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeCreateDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseDetailDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeResponseShortDTO;
import ru.trainee.creditmanager.dto.creditType.CreditTypeUpdateDTO;
import ru.trainee.creditmanager.entity.CreditType;
import ru.trainee.creditmanager.mapper.creditType.CreditTypeMapper;
import ru.trainee.creditmanager.service.CreditTypeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/credit-types")
@Tag(name = "Типы кредитов", description = "Операции над карточками типов кредитов")
public class CreditTypeControllerV1 {

    private final CreditTypeService creditTypeService;
    private final CreditTypeMapper creditTypeMapper;

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

        Optional<CreditType> creditTypeOptional = creditTypeService.findByName(dto.getName());
        if(creditTypeOptional.isEmpty()){
            return creditTypeMapper.toCreditTypeDetailDto(
                    creditTypeService.create(
                            creditTypeMapper.toCreditTypeEntity(dto))
            );
        } else {
            throw new EntityExistsException("Credit type already exixst.");
        }
    }


    @GetMapping
    @Operation(
            summary = "Получение списка всех типов кредитов",
            description = """
                    Список содержит краткие карточки типов кредитов.
                    """
    )
    public PageableListResponseDTO<CreditTypeResponseShortDTO> readAll(Pageable p){

        Page<CreditType> page = creditTypeService.readAll(p);

        return new PageableListResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getContent().stream()
                        .map(creditTypeMapper::toCreditTypeShortDto)
                        .toList());
    }




    @GetMapping("/{id}")
    @Operation(
            summary = "Получение карточки типа кредита",
            description = """
                              Карточка типа кредита содержит...
                              """
    )
    public CreditTypeResponseDetailDTO findById(@PathVariable UUID id) {
        Optional<CreditType> creditTypeOptional = creditTypeService.findById(id);
        if(creditTypeOptional.isPresent()){
            return creditTypeMapper.toCreditTypeDetailDto(creditTypeOptional.get());
        } else {
            throw new EntityNotFoundException("Credit type " + id + " not found! Check id.");
        }
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
    public CreditTypeResponseDetailDTO update(@RequestBody CreditTypeCreateDTO dto){
        return creditTypeMapper.toCreditTypeDetailDto(
                creditTypeService.update(
                        creditTypeMapper.toCreditTypeEntity(dto))
        );
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление карточки типа кредита",
            description = """
                              Карточка просто удаляется...
                              """
    )
    public void delete(@PathVariable UUID id) {
        if(creditTypeService.findById(id).isEmpty()){
            throw new EntityNotFoundException("Credit type " + id + " not found! Check id.");
        }
        creditTypeService.delete(id);
    }
}
