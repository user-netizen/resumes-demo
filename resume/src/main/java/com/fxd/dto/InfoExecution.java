package com.fxd.dto;

import com.fxd.entity.ResumesInfo;
import com.fxd.enums.StateEnum;

import java.util.List;

/**
 * 存储相关信息和状态值
 */
public class InfoExecution {

    //结果状态
    int state;
    //状态标识
    String stateInfo;
    //数量
    private int count;
    //操作（增删改的时候用到）
    private ResumesInfo resumesInfo;
    //操作列表（查询列表的时候使用）
    private List<ResumesInfo> resumesInfoList;

    public InfoExecution() {
    }
    //操作失败的时候使用构造器
    public InfoExecution(StateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //操作成功对象的时候使用构造器
    public InfoExecution(StateEnum stateEnum, ResumesInfo resumesInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.resumesInfo = resumesInfo;
    }
    //操作成功List的时候使用构造器
    public InfoExecution(StateEnum stateEnum, List<ResumesInfo> resumesInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.resumesInfoList = resumesInfoList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ResumesInfo getResumesInfo() {
        return resumesInfo;
    }

    public void setResumesInfo(ResumesInfo resumesInfo) {
        this.resumesInfo = resumesInfo;
    }

    public List<ResumesInfo> getResumesInfoList() {
        return resumesInfoList;
    }

    public void setResumesInfoList(List<ResumesInfo> resumesInfoList) {
        this.resumesInfoList = resumesInfoList;
    }
}
