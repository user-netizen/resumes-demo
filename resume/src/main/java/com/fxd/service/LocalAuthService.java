package com.fxd.service;

import com.fxd.dto.LocalAuthExecution;
import com.fxd.entity.LocalAuth;
import com.fxd.exception.LocalAuthException;

public interface LocalAuthService {

    /**
     * 获取单个数据
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuth(Long userId);

    /**
     * 添加单个数据
     *
     * @param localAuth
     * @return
     * @throws LocalAuthException
     */
    LocalAuthExecution addLocalAuth(LocalAuth localAuth) throws LocalAuthException;
}
