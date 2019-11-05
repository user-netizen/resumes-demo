package com.fxd.dao;

import com.fxd.entity.ResumesInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 1.新添加用户简历
 * 2.根据用户Id更新简历数据
 * 3.获取所有的简历数据List
 * 4.根据用户ID获取简历数据
 */
@Component("ResumesInfoDao")
public interface ResumesInfoDao {

    /**
     * 新添加用户简历
     *
     * @param resumesInfo
     * @return 受影响行数
     */
    int insertResumesInfo(ResumesInfo resumesInfo);

    /**
     * 根据用户Id更新简历数据
     *
     * @param resumesInfo
     * @return 受影响行数
     */
    int updateResumesInfo(ResumesInfo resumesInfo);

    /**
     * 根据用户给出的 查询条件 获取数据list
     * @param resumesInfoCondition 查询条件
     * @param rowIndex 从第几条数据开始
     * @param pageSize 每次请求返回数据量
     * @return 分页返回 简历数据
     */
    List<ResumesInfo> queryResumesInfoList(
            @Param("resumesInfoCondition") ResumesInfo resumesInfoCondition,
            @Param("rowIndex") int rowIndex,
            @Param("pageSize") int pageSize);

    /**
     * 根据用户给出的 查询条件 获取数据list数量
     * @param resumesInfoCondition
     * @return 获取数据list 数量
     */
    int queryResumesInfoCount(
            @Param("resumesInfoCondition") ResumesInfo resumesInfoCondition);

    /**
     * 根据用户ID 获取数据
     * @param userId
     * @return 实体ResumesInfo
     */
    ResumesInfo queryResumesInfoId(Long userId);

    /**
     * 查询单个数据 条件自身ID
     * @param infoId
     * @return
     */
    ResumesInfo queryResumesInfoByInfoId(Long infoId);
}
