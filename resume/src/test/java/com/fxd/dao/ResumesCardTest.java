package com.fxd.dao;

import com.fxd.entity.PersonInfo;
import com.fxd.entity.ResumesCard;
import com.fxd.entity.ResumesInfo;
import com.fxd.util.HttpServletRequestUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResumesCardTest {

    @Autowired
    private ResumesCardDao resumesCardDao;

    private ResumesInfo resumesInfo;
    private ResumesCard resumesCard;
    private PersonInfo personInfo;

    @Test
    @Ignore
    public void name() {
        personInfo = new PersonInfo();
        personInfo.setUserId(21L);

        resumesInfo = new ResumesInfo();
        resumesInfo.setResumesInfoId(6L);

        resumesCard = new ResumesCard();
        resumesCard.setPersonInfo(personInfo);
        resumesCard.setResumesInfo(resumesInfo);
        resumesCard.setResumesCardDesc("oracle测试描述");
        int effectNum = resumesCardDao.insertResumesCard(resumesCard);
        System.out.println("受影响行数：" + effectNum);
    }

    @Test
    @Ignore
    public void queryAllResumesCardList() {
        //查询条件
        String name = "";
        //查询固定条件
        int pageIndex = 0;
        int pageSize = 4;

        resumesInfo = new ResumesInfo();
        resumesInfo.setResumesInfoName(name);

        resumesCard = new ResumesCard();
        resumesCard.setResumesInfo(resumesInfo);

        List<ResumesCard> list = resumesCardDao.queryAllResumesCardList(resumesCard, pageIndex, pageSize);

        System.out.println(list.size());
    }

    @Test
    @Ignore
    public void queryUserIdResumesCard() {
        ResumesCard resumesCard = resumesCardDao.queryUserIdResumesCard(1L);

        System.out.println(resumesCard.getResumesCardDesc());
    }

}
