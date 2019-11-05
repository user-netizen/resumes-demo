package com.fxd.dto;

import com.fxd.entity.PersonInfo;
import com.fxd.enums.StateEnum;

import java.util.List;

/**
 * 存储相关信息和状态值
 */
public class PersonExecution {

    //结果状态
    int state;
    //状态标识
    String stateInfo;
    //数量
    private int count;
    //操作（增删改的时候用到）
    private PersonInfo personInfo;
    //操作列表（查询列表的时候使用）
    private List<PersonInfo> personInfoList;

    public PersonExecution() {
    }
    //操作失败的时候使用构造器
    public PersonExecution(StateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //操作成功对象的时候使用构造器
    public PersonExecution(StateEnum stateEnum, PersonInfo personInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.personInfo = personInfo;
    }
    //操作成功List的时候使用构造器
    public PersonExecution(StateEnum stateEnum, List<PersonInfo> personInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.personInfoList = personInfoList;
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

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public List<PersonInfo> getPersonInfoList() {
        return personInfoList;
    }

    public void setPersonInfoList(List<PersonInfo> personInfoList) {
        this.personInfoList = personInfoList;
    }
}
