package com.fxd.service;

import com.fxd.dto.InfoExecution;
import com.fxd.entity.ResumesInfo;
import com.fxd.exception.InfoException;

import java.io.InputStream;

public interface ResumesInfoService {

    /**
     * 通过用户ID 获取用户简历
     *
     * @param userId
     * @return
     */
    ResumesInfo getResumesInfoByUserId(Long userId);

    /**
     * 查询单个数据 条件infoID
     *
     * @param infoId
     * @return
     */
    ResumesInfo getResumesInfoByInfoId(Long infoId);

    /**
     * 分页获取列表数据
     *
     * @param resumesInfo
     * @param pageIndex   缺少PageCalculator工具类
     * @param pageSize
     * @return
     */
    InfoExecution getResumesInfoList(ResumesInfo resumesInfo, int pageIndex, int pageSize);

    /**
     * 添加数据
     *
     * @param resumesInfo    servlet调用service 传入数据
     * @param imgInputStream 传入图片数据
     * @param fileName       传入图片name
     * @return
     * @throws InfoException
     */
    InfoExecution addResumesInfo(ResumesInfo resumesInfo,
                                 InputStream imgInputStream,
                                 String fileName) throws InfoException;

    /**
     * 更新数据
     *
     * @param resumesInfo
     * @param imgInputStream
     * @param fileName
     * @return
     * @throws InfoException
     */
    InfoExecution modifyResumesInfo(ResumesInfo resumesInfo,
                                    InputStream imgInputStream,
                                    String fileName) throws InfoException;
}
