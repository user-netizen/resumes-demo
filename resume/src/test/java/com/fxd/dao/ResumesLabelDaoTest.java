package com.fxd.dao;

import com.fxd.entity.ResumesCard;
import com.fxd.entity.ResumesLabel;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResumesLabelDaoTest {

    @Autowired
    private ResumesLabelDao resumesLabelDao;

    @Test
    @Ignore
    public void name() {
        ResumesCard resumesCard = new ResumesCard();
        resumesCard.setResumesCardId(9L);

        ResumesLabel resumesLabel1 = new ResumesLabel();
        resumesLabel1.setResumesLabelName("出国");
        resumesLabel1.setPriority(1);
        resumesLabel1.setCreateTime(new Date());
        resumesLabel1.setLastEditTime(new Date());
        resumesLabel1.setResumesCard(resumesCard);


        ResumesLabel resumesLabel2 = new ResumesLabel();
        resumesLabel2.setResumesLabelName("国考");
        resumesLabel2.setPriority(8);
        resumesLabel2.setCreateTime(new Date());
        resumesLabel2.setLastEditTime(new Date());
        resumesLabel2.setResumesCard(resumesCard);

        List<ResumesLabel> arrayList = new ArrayList<ResumesLabel>();

        arrayList.add(resumesLabel1);
        arrayList.add(resumesLabel2);

        int effectNum = resumesLabelDao.batchInsertResumesLabel(arrayList);

        System.out.println("受影响:" + effectNum);
    }
}
