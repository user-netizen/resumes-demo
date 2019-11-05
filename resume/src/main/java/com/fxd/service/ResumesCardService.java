package com.fxd.service;

import com.fxd.dto.CardExecution;
import com.fxd.entity.ResumesCard;
import com.fxd.exception.CardException;

public interface ResumesCardService {
    /**
     * 查询多个数据 条件Dao.xml自定义
     * @param resumesCardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    CardExecution getResumesCardList(ResumesCard resumesCardCondition, int pageIndex, int pageSize);

    /**
     * 查询多个数据量 条件Dao.xml自定义
     * @param resumesCardCondition
     * @return
     * @throws CardException
     */
    CardExecution getResumesCardCount(ResumesCard resumesCardCondition);

    /**
     * 查询数据 条件用户ID
     *
     * @param userId
     * @return
     */
    ResumesCard getResumesCardByUserId(Long userId);

    /**
     * 添加数据 条件当前用户ID
     * @param resumesCard
     * @return
     * @throws CardException
     */
    CardExecution addResumesCard(ResumesCard resumesCard) throws CardException;

    /**
     * 更新数据 条件当前用户ID
     * @param resumesCard
     * @return
     * @throws CardException
     */
    CardExecution updateResumesCard(ResumesCard resumesCard) throws CardException;
}
