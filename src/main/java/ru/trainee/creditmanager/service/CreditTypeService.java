package ru.trainee.creditmanager.service;

import ru.trainee.creditmanager.entity.CreditType;

public interface CreditTypeService extends CommonService<CreditType>{

    CreditType findByName(String name);

}