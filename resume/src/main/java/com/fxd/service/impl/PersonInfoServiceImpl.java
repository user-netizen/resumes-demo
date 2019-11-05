package com.fxd.service.impl;

import com.fxd.dao.PersonInfoDao;
import com.fxd.dto.PersonExecution;
import com.fxd.entity.PersonInfo;
import com.fxd.enums.StateEnum;
import com.fxd.exception.PersonException;
import com.fxd.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfo(String email) {
        return personInfoDao.queryPersonInfoByEmail(email);
    }

    @Override
    public PersonExecution addPersonInfo(PersonInfo personInfo) throws PersonException {
        if (personInfo == null) {
            return new PersonExecution(StateEnum.NULL_INFO);
        } else {
            try {
                int effectNum = personInfoDao.insertPersonInfo(personInfo);
                if (effectNum > 0) {
                    return new PersonExecution(StateEnum.SUCCESS);
                }else {
                    return new PersonExecution(StateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                System.out.println("addPersonInfo："+e.getMessage());
                throw new PersonException("error: " + e.getMessage());
            }
        }
    }
}
