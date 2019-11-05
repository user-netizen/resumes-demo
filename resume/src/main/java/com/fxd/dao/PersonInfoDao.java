package com.fxd.dao;

import com.fxd.entity.PersonInfo;
import org.springframework.stereotype.Component;

@Component
public interface PersonInfoDao {
    /**
     * 查询单个数据 条件：email
     * @param email
     * @return
     */
    PersonInfo queryPersonInfoByEmail(String email);

    /**
     * 添加单个数据 添加：name，email
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);
}
