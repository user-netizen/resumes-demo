package com.fxd.dao;

import com.fxd.entity.LocalAuth;
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
public class LocalAuthDaoTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    @Ignore
    public void insertLocalAuth() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(25L);

        LocalAuth localAuth = new LocalAuth();
        localAuth.setUsername("离里");
        localAuth.setPassword("111111");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());

        localAuth.setPersonInfo(personInfo);

        int effectNum = localAuthDao.insertLocalAuth(localAuth);

        System.out.println("受影响的：" + effectNum);
    }
}
