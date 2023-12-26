package ru.trainee.creditmanager.repository;

import org.springframework.stereotype.Repository;
import ru.trainee.creditmanager.entity.CreditType;

import java.util.Optional;

@Repository
public interface CreditTypeRepository extends CommonRepository<CreditType> {

    Optional<CreditType> findByName(String name);


}