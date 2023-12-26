package ru.trainee.creditmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.trainee.creditmanager.entity.BaseEntity;

import java.util.UUID;

@NoRepositoryBean
public interface CommonRepository<E extends BaseEntity> extends JpaRepository<E, UUID> {

}
