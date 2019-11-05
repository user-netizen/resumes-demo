package com.fxd.dao;

import com.fxd.entity.PersonInfo;
import com.fxd.entity.ResumesInfo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResumesInfoDaoTest {

    @Autowired
    private ResumesInfoDao resumesInfoDao;

    @Test
    @Ignore
    public void insertResumesInfo() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(27L);

        ResumesInfo resumesInfo = new ResumesInfo();
        resumesInfo.setPersonInfo(personInfo);
        resumesInfo.setResumesInfoName("1");
        resumesInfo.setResumesInfoGender("1");
        resumesInfo.setResumesInfoAge(1);
        resumesInfo.setBornYear("1");
        resumesInfo.setBornCity("1");
        resumesInfo.setResumesInfoEducation("1");
        resumesInfo.setGraduationCity("1");
        resumesInfo.setContactWay("1");
        resumesInfo.setContactAddr("1");
        resumesInfo.setWorkTimeLimit(1);
        resumesInfo.setWorkExperience("1");
        resumesInfo.setSelfIntroduction("1");
        resumesInfo.setResumesUserImg("");
        resumesInfo.setPageView(35);
        resumesInfo.setEditNumber(1);
        resumesInfo.setPriority(0);
        resumesInfo.setCreateTime(new Date());
        resumesInfo.setLastEditTime(new Date());
        resumesInfo.setEnableStatus(1);
        resumesInfo.setAdvice("所有人可见");

        int effectNum = resumesInfoDao.insertResumesInfo(resumesInfo);
        System.out.println("受影响的行数：" + effectNum);
    }
}
