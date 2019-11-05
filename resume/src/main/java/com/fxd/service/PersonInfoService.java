package com.fxd.service;

import com.fxd.dto.PersonExecution;
import com.fxd.entity.PersonInfo;
import com.fxd.exception.PersonException;

public interface PersonInfoService {

    /**
     * 获取单个数据
     * @param email
     * @return
     */
    PersonInfo getPersonInfo(String email);

    /**
     * 添加单个数据
     * @param personInfo
     * @return
     */
    PersonExecution addPersonInfo(PersonInfo personInfo) throws PersonException;
}
