package com.fxd.dto;

import com.fxd.entity.LocalAuth;
import com.fxd.enums.StateEnum;

import java.util.List;

/**
 * 存储相关信息和状态值
 */
public class LocalAuthExecution {

    //结果状态
    int state;
    //状态标识
    String stateInfo;
    //数量
    private int count;
    //操作（增删改的时候用到）
    private LocalAuth localAuth;
    //操作列表（查询列表的时候使用）
    private List<LocalAuth> localAuthList;

    public LocalAuthExecution() {
    }
    //操作失败的时候使用构造器
    public LocalAuthExecution(StateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //操作成功对象的时候使用构造器
    public LocalAuthExecution(StateEnum stateEnum, LocalAuth localAuth) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.localAuth = localAuth;
    }
    //操作成功List的时候使用构造器
    public LocalAuthExecution(StateEnum stateEnum, List<LocalAuth> localAuthList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.localAuthList = localAuthList;
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

    public LocalAuth getLocalAuth() {
        return localAuth;
    }

    public void setLocalAuth(LocalAuth localAuth) {
        this.localAuth = localAuth;
    }

    public List<LocalAuth> getLocalAuthList() {
        return localAuthList;
    }

    public void setLocalAuthList(List<LocalAuth> localAuthList) {
        this.localAuthList = localAuthList;
    }
}
