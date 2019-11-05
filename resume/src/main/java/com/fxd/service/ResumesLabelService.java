package com.fxd.service;

import com.fxd.dto.LabelExecution;
import com.fxd.entity.ResumesLabel;
import com.fxd.exception.LabelException;

import java.util.List;

public interface ResumesLabelService {
    /**
     * 查询单条数据 条件CardId
     * @param cardId
     * @return
     */
    LabelExecution getLabelByCardIdList(long cardId);

    /**
     *
     * @param cardId
     * @return
     */
    ResumesLabel getLabelByCardId(long cardId);

    /**
     * 批量添加 条件当前用户ID和resumesInfoId
     * @param resumesLabelList
     * @return
     */
    LabelExecution addBatchResumesLabel(List<ResumesLabel> resumesLabelList) throws LabelException;
}
