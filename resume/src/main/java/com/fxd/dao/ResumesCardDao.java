package com.fxd.dao;

import com.fxd.entity.ResumesCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 简历卡片 Dao操作
 * 1.根据用户ID 查询数据
 * 2.根据当前用户ID和卡片ID 编辑简历描述
 * 3.删除
 * 4.新插入数据
 */
@Component
public interface ResumesCardDao {
    /**
     * 查询操作 据userId查询卡片信息
     * @param userId
     * @return 返回实体对象
     */
    ResumesCard queryUserIdResumesCard(Long userId);

    /**
     * 查询总数据 条件Dao自定义
     * @param resumesCardCondition
     * @param rowIndex 从第几条数据开始
     * @param pageSize 每次请求返回数据量
     * @return
     */
    List<ResumesCard> queryAllResumesCardList(
            @Param("resumesCardCondition") ResumesCard resumesCardCondition,
            @Param("rowIndex") int rowIndex,
            @Param("pageSize") int pageSize);

    /**
     * 查询总数量数据 条件Dao自定义
     * @param resumesCardCondition
     * @return
     */
    int queryResumesCardCount(@Param("resumesCardCondition") ResumesCard resumesCardCondition);

    /**
     * 插入操作 新增简历卡片
     *
     * @param resumesCard
     * @return 受影响行数
     */
    int insertResumesCard(ResumesCard resumesCard);

    /**
     * 更新操作 编辑简历卡片
     *
     * @param resumesCard
     * @return 受影响行数
     */
    int updateResumesCard(ResumesCard resumesCard);

    /**
     * 删除操作 暂定
     * @param resumesCard
     * @return 受影响行数
     */
    int deletResumesCard(ResumesCard resumesCard);
}
