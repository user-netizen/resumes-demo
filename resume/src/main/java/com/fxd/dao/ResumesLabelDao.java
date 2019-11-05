package com.fxd.dao;

import com.fxd.entity.ResumesLabel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 简历的标签
 * 1.批量添加标签
 * 2.删除
 * 3.userId查询所有的标签
 */
@Component("ResumesLabelDao")
public interface ResumesLabelDao {

    /**
     * 查询 cardId查询所有标签
     *
     * @param resumesCardId
     * @return 返回list集合
     */
    List<ResumesLabel> queryResumesLabelList(Long resumesCardId);

    /**
     * 查询数据量 cardId查询所有标签
     * @param resumesCardId
     * @return
     */
    int queryResumesLabelCount(Long resumesCardId);
    /**
     *
     * @param resumesCardId
     * @return
     */
    ResumesLabel queryResumesLabel(Long resumesCardId);

    /**
     * 插入 批量插入标签
     *
     * @param resumesLabelList
     * @return
     */
    int batchInsertResumesLabel(List<ResumesLabel> resumesLabelList);
}
