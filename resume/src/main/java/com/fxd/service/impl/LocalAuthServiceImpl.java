package com.fxd.service.impl;

import com.fxd.dao.LocalAuthDao;
import com.fxd.dto.LocalAuthExecution;
import com.fxd.entity.LocalAuth;
import com.fxd.enums.StateEnum;
import com.fxd.exception.CardException;
import com.fxd.exception.LocalAuthException;
import com.fxd.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    /**
     * 获取单个数据
     * @param userId
     * @return
     */
    @Override
    public LocalAuth getLocalAuth(Long userId) {
        return localAuthDao.queryLocalAuthByUserId(userId);
    }

    /**
     * 添加单个数据
     * @param localAuth
     * @return
     * @throws LocalAuthException
     */
    @Override
    public LocalAuthExecution addLocalAuth(LocalAuth localAuth) throws LocalAuthException {
        if (localAuth == null) {
            return new LocalAuthExecution(StateEnum.NULL_INFO);
        }
        try {
            int effectNum = localAuthDao.insertLocalAuth(localAuth);

            if (effectNum > 0) {
                return new LocalAuthExecution(StateEnum.SUCCESS);
            } else {
                return new LocalAuthExecution(StateEnum.INNER_ERROR);
            }
        } catch (Exception e) {
            System.out.println("addLocalAuth："+e.getMessage());
            throw new CardException(e.getMessage());
        }
    }
}
