package com.fxd.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxd.cache.JedisUtil;
import com.fxd.dao.ResumesInfoDao;
import com.fxd.dto.InfoExecution;
import com.fxd.entity.ResumesInfo;
import com.fxd.enums.StateEnum;
import com.fxd.exception.InfoException;
import com.fxd.service.ResumesInfoService;
import com.fxd.util.ImageUtil;
import com.fxd.util.PageCalculator;
import com.fxd.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class ResumesInfoServiceImpl implements ResumesInfoService {
    @Autowired
    private ResumesInfoDao resumesInfoDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static String INFOBYUSERID = "infobyuserid";

    @Override
    public InfoExecution addResumesInfo(ResumesInfo resumesInfo,
                                        InputStream imgInputStream,
                                        String fileName) {

        if (resumesInfo == null) {
            return new InfoExecution(StateEnum.NULL_INFO);
        }
        try {
            //给其余不需要前端提交值的，属性赋值
            //注oracle 特殊 参数全部带上
            resumesInfo.setResumesUserImg("");
            resumesInfo.setPageView(1);
            resumesInfo.setEditNumber(1);//每次只要插入成功，就自增
            resumesInfo.setPriority(0);
            resumesInfo.setCreateTime(new Date());
            resumesInfo.setLastEditTime(new Date());
            resumesInfo.setEnableStatus(1);
            resumesInfo.setAdvice("所有人可见");

            int effectNum = resumesInfoDao.insertResumesInfo(resumesInfo);
            if (effectNum <= 0) {
                throw new InfoException("创建 插入数据库 失败");
            } else {
                if (imgInputStream != null) {
                    try {
                        //单独处理不影响 其他数据的插入数据库
                        addImage(resumesInfo, imgInputStream, fileName);
                    } catch (Exception e) {
                        throw new InfoException("Image单独处理 error:" + e.getMessage());
                    }
                    effectNum = resumesInfoDao.updateResumesInfo(resumesInfo);
                    if (effectNum <= 0) {
                        throw new InfoException("图片添加数据库错误");
                    }
                }
            }
        } catch (Exception e) {
            throw new InfoException("从Servlet传来的数据添加 数据库失败");
        }
        return new InfoExecution(StateEnum.SUCCESS, resumesInfo);
    }

    /**
     * 注意点 更新 保证当前用户ID 在info表中只用一条数据
     *
     * @param resumesInfo
     * @param imgInputStream
     * @param fileName
     * @return
     * @throws InfoException
     */
    @Override
    public InfoExecution modifyResumesInfo(ResumesInfo resumesInfo, InputStream imgInputStream, String fileName) throws InfoException {
        long userId = resumesInfo.getPersonInfo().getUserId();
        //定义redis的可以
        String key = INFOBYUSERID + userId;

        if (resumesInfo == null || resumesInfo.getPersonInfo().getUserId() == null) {
            return new InfoExecution(StateEnum.NULL_INFO.NULL_ID);
        } else {
            try {
                resumesInfo.setLastEditTime(new Date());
                //先判断图片是否更新
                if (imgInputStream != null && !"".equals(fileName)) {
                    //建立临时的tempResumesInfo查询出此ID的img数据库中路径并删除
                    ResumesInfo tempResumesInfo = resumesInfoDao.queryResumesInfoId(resumesInfo.getPersonInfo().getUserId());
                    if (tempResumesInfo.getResumesUserImg() != null) {
                        ImageUtil.deleteFileOrPath(tempResumesInfo.getResumesUserImg());
                    }

                    //没有 直接创建路径 上传图片
                    addImage(tempResumesInfo, imgInputStream, fileName);
                    String imgPath = tempResumesInfo.getResumesUserImg();
                    resumesInfo.setResumesUserImg(imgPath);
                }

                int effectNum = resumesInfoDao.updateResumesInfo(resumesInfo);

                if (effectNum <= 0) {
                    return new InfoExecution(StateEnum.INNER_ERROR);
                } else {
                    //让其调用者，知道数据已改 并返回数据，存入更新redis
                    //从数据库里面取出相应数据
                    resumesInfo = resumesInfoDao.queryResumesInfoId(userId);

                    //成功，在redis中存入最新数据
                    //定义jackson数据转换操作类
                    ObjectMapper mapper = new ObjectMapper();
                    //不管key是否存在，set(key,更新数据)
                    //将相关的实体类集合转换成string，存入redis里面对应的key中
                    String jsonString = mapper.writeValueAsString(resumesInfo);
                    //存在key，存入redis-key更新数据
                    jedisStrings.set(key, jsonString);

                    return new InfoExecution(StateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new InfoException("modify error: " + e.getMessage());
            }
        }
    }

    /**
     * 通过用户ID 获取用户简历
     *
     * @param userId
     * @return
     */
    @Override
    public ResumesInfo getResumesInfoByUserId(Long userId) {
        /**
         * 再次之前 redis启动server服务
         * redis缓存思路
         * 当用户登录，userid查询resumesinfo
         * 先查询redis
         * 没有数据，查询mysql
         * 存在数据，返回，并插入redis
         */
        //定义redis的可以
        String key = INFOBYUSERID + userId;
        //定义接受对象
        ResumesInfo resumesInfo;
        //定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        try {
            //判断key是否存在

            if (!jedisKeys.exists(key) || !jedisStrings.get(key).isEmpty()) {
                //如不存在数据，从数据库里面取出相应数据
                resumesInfo = resumesInfoDao.queryResumesInfoId(userId);
                try {
                    //将相关的实体类集合转换成string，存入redis里面对应的key中
                    String jsonString = mapper.writeValueAsString(resumesInfo);
                    jedisStrings.set(key, jsonString);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new InfoException(e.getMessage());
                }
            } else {
                String jsonString = jedisStrings.get(key);
                try {
//                    System.out.println(jsonString);
                    resumesInfo = mapper.readValue(jsonString, ResumesInfo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new InfoException(e.getMessage());
                }
            }
            return resumesInfo;
        } catch (Exception e) {
            //如redis服务器出现异常，直接查询数据库返回
            System.out.println(e.getMessage());
            return resumesInfoDao.queryResumesInfoId(userId);
        }
    }

    /**
     * 获取单个数据 条件infoId
     *
     * @param infoId
     * @return
     */
    @Override
    public ResumesInfo getResumesInfoByInfoId(Long infoId) {
        return resumesInfoDao.queryResumesInfoByInfoId(infoId);
    }

    /**
     * 分页获取列表数据
     *
     * @param resumesInfo
     * @param pageIndex   缺少PageCalculator工具类
     * @param pageSize
     * @return infoExecution存储相关查询list值
     */
    @Override
    public InfoExecution getResumesInfoList(ResumesInfo resumesInfo, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        //获取查询的 数据List
        List<ResumesInfo> resumesInfoList = resumesInfoDao.queryResumesInfoList(resumesInfo, rowIndex, pageSize);
        //获取查询的 数据总量
        int count = resumesInfoDao.queryResumesInfoCount(resumesInfo);

        InfoExecution infoExecution = new InfoExecution();
        if (resumesInfoList != null) {
            infoExecution.setResumesInfoList(resumesInfoList);
            infoExecution.setCount(count);
        } else {
            infoExecution.setState(StateEnum.INNER_ERROR.getState());
        }
        return infoExecution;
    }

    /**
     * 单独处理图片数据
     *
     * @param resumesInfo
     * @param inputStream
     * @param filename
     */
    private void addImage(ResumesInfo resumesInfo, InputStream inputStream, String filename) {
        //获取 图片上传的目标路径
        String dest = PathUtil.getCostumeImagePath(resumesInfo.getResumesInfoId());
        //获取图片数据处理成功的 路径
        String imageAddr = ImageUtil.generateThumbnail(inputStream, filename, dest);
        //给bean对象图片属性赋值
        resumesInfo.setResumesUserImg(imageAddr);
    }
}
