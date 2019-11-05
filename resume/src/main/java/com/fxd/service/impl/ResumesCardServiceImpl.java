package com.fxd.service.impl;

import com.fxd.dao.ResumesCardDao;
import com.fxd.dto.CardExecution;
import com.fxd.entity.ResumesCard;
import com.fxd.enums.StateEnum;
import com.fxd.exception.CardException;
import com.fxd.service.ResumesCardService;
import com.fxd.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResumesCardServiceImpl implements ResumesCardService {
    @Autowired
    private ResumesCardDao resumesCardDao;

    /**
     * 添加数据 条件当前用户ID
     *
     * @param resumesCard
     * @return
     * @throws CardException
     */
    @Override
    public CardExecution addResumesCard(ResumesCard resumesCard) throws CardException {
        if (resumesCard == null) {
            return new CardExecution(StateEnum.NULL_ID);
        } else {
            try {
                int effectNum = resumesCardDao.insertResumesCard(resumesCard);
                if (effectNum <= 0) {
                    return new CardExecution(StateEnum.INNER_ERROR);
                } else {
                    System.out.println(resumesCard.getPersonInfo().getUserId());
                    resumesCard = resumesCardDao.queryUserIdResumesCard(resumesCard.getPersonInfo().getUserId());
                    return new CardExecution(StateEnum.SUCCESS, resumesCard);
                }
            } catch (Exception e) {
                throw new CardException("error: " + e.getMessage());
            }
        }
    }

    /**
     * 更新数据 条件当前用户ID
     *
     * @param resumesCard
     * @return
     * @throws CardException
     */
    @Override
    public CardExecution updateResumesCard(ResumesCard resumesCard) throws CardException {
        if (resumesCard == null) {
            return new CardExecution(StateEnum.NULL_INFO);
        } else {
            try {
                int effectNum = resumesCardDao.updateResumesCard(resumesCard);
                if (effectNum <= 0) {
                    return new CardExecution(StateEnum.INNER_ERROR);
                } else {
                    resumesCard = resumesCardDao.queryUserIdResumesCard(resumesCard.getPersonInfo().getUserId());
                    return new CardExecution(StateEnum.SUCCESS, resumesCard);
                }
            } catch (Exception e) {
                throw new CardException("error: " + e.getMessage());
            }
        }
    }

    /**
     * 获取多个数据 条件ResumesCardDao.xml自定义
     *
     * @param resumesCardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public CardExecution getResumesCardList(ResumesCard resumesCardCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);

        List<ResumesCard> resumesCardList = resumesCardDao.queryAllResumesCardList(resumesCardCondition, rowIndex, pageSize);

        int count = resumesCardDao.queryResumesCardCount(resumesCardCondition);

        CardExecution ce = new CardExecution();
        if (resumesCardList != null) {
            ce.setResumesCardList(resumesCardList);
            ce.setCount(count);
        } else {
            ce.setState(StateEnum.INNER_ERROR.getState());
        }
        return ce;
    }

    /**
     * 获取多个数据量 条件ResumesCardDao.xml自定义
     *
     * @param resumesCardCondition
     * @return
     */
    @Override
    public CardExecution getResumesCardCount(ResumesCard resumesCardCondition) {
        int count = resumesCardDao.queryResumesCardCount(resumesCardCondition);
        CardExecution ce = new CardExecution();
        ce.setCount(count);
        return ce;
    }

    /**
     * 查询数据 条件用户ID
     *
     * @param userId
     * @return
     */
    @Override
    public ResumesCard getResumesCardByUserId(Long userId) {
        ResumesCard resumesCard = resumesCardDao.queryUserIdResumesCard(userId);
        return resumesCard;
    }

}
