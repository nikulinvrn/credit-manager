package ru.trainee.creditmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.trainee.creditmanager.entity.CreditType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditTypeRepository extends JpaRepository<CreditType, UUID> {

    Optional<CreditType> findByName(String name);


}