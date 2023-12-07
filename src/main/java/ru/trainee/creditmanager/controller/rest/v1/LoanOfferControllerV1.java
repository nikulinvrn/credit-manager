package ru.trainee.creditmanager.controller.rest.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferCreateDTO;
import ru.trainee.creditmanager.dto.loanOffer.LoanOfferResponseDetailDTO;
import ru.trainee.creditmanager.service.LoanOfferService;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/offers")
public class LoanOfferControllerV1 {


    private final LoanOfferService loanOfferService;

    @PostMapping
    public LoanOfferResponseDetailDTO create(@RequestBody LoanOfferCreateDTO dto) {
        return loanOfferService.create(dto);
    }
//
//    @GetMapping("accepting/{id}")
//    public ResponseEntity<LoanOffer> accepting(@PathVariable Long id){
//        return new ResponseEntity<>(loanOfferService.accepting(id), HttpStatus.OK);
//    }

}
