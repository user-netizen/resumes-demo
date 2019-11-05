package com.fxd.dao;

import com.fxd.entity.LocalAuth;
import org.springframework.stereotype.Component;

@Component
public interface LocalAuthDao {

    /**
     * 添加数据 条件localAuth
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 查询单个数据 条件pwd
     * @param userId
     * @return
     */
    LocalAuth queryLocalAuthByUserId(Long userId);
}
