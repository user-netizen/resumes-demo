package com.fxd.service.impl;

import com.fxd.dao.ResumesLabelDao;
import com.fxd.dto.LabelExecution;
import com.fxd.entity.ResumesLabel;
import com.fxd.enums.StateEnum;
import com.fxd.exception.LabelException;
import com.fxd.service.ResumesLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResumesLabelServiceImpl implements ResumesLabelService {
    @Autowired
    private ResumesLabelDao resumesLabelDao;

    @Override
    public LabelExecution getLabelByCardIdList(long cardId) {
        //获取查询的 数据List
        List<ResumesLabel> resumesLabelList = resumesLabelDao.queryResumesLabelList(cardId);
        //获取查询的 数据总量
        int count = resumesLabelDao.queryResumesLabelCount(cardId);

        LabelExecution labelExecution = new LabelExecution();
        if (resumesLabelList != null) {
            labelExecution.setResumesLabelList(resumesLabelList);
            labelExecution.setCount(count);
        } else {
            labelExecution.setState(StateEnum.INNER_ERROR.getState());
        }
        return labelExecution;
    }

    @Override
    public ResumesLabel getLabelByCardId(long cardId) {
        return resumesLabelDao.queryResumesLabel(cardId);
    }

    @Override
    public LabelExecution addBatchResumesLabel(List<ResumesLabel> resumesLabelList) throws LabelException {
        if (resumesLabelList != null && resumesLabelList.size() > 0) {
            try {
                int effectNum = resumesLabelDao.batchInsertResumesLabel(resumesLabelList);
                if (effectNum <= 0) {
                    throw new LabelException("批量添加失败");
                } else {
                    return new LabelExecution(StateEnum.SUCCESS, resumesLabelList);
                }
            } catch (Exception e) {
                throw new LabelException("error: " + e.getMessage());
            }
        } else {
            return new LabelExecution(StateEnum.EMPTY_LIST);
        }
    }
}
