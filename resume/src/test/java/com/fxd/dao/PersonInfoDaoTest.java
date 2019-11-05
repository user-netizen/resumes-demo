package com.fxd.dao;

import com.fxd.entity.PersonInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoDaoTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    @Ignore
    public void insertPersonInfo() {

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("Oracle");
        personInfo.setProfileImg("");
        personInfo.setEmail("123456789@123.com");
        personInfo.setGender("男");
        personInfo.setEnableStatus(0);
        personInfo.setUserType(1);
        personInfo.setCreatTime(new Date());
        personInfo.setLastEditTime(new Date());

        int effectNum = personInfoDao.insertPersonInfo(personInfo);
        System.out.println("受影响行数：" + effectNum);

    }
}
