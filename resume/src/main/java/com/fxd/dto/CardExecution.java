package com.fxd.dto;

import com.fxd.entity.ResumesCard;
import com.fxd.enums.StateEnum;

import java.util.List;

/**
 * 存储相关信息和状态值
 */
public class CardExecution {

    //结果状态
    int state;
    //状态标识
    String stateInfo;
    //数量
    private int count;
    //操作（增删改的时候用到）
    private ResumesCard resumesCard;
    //操作列表（查询列表的时候使用）
    private List<ResumesCard> resumesCardList;

    public CardExecution() {
    }
    //操作失败的时候使用构造器
    public CardExecution(StateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //操作成功对象的时候使用构造器
    public CardExecution(StateEnum stateEnum, ResumesCard resumesCard) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.resumesCard = resumesCard;
    }
    //操作成功List的时候使用构造器
    public CardExecution(StateEnum stateEnum, List<ResumesCard> resumesCardList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.resumesCardList = resumesCardList;
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

    public ResumesCard getResumesCard() {
        return resumesCard;
    }

    public void setResumesCard(ResumesCard resumesCard) {
        this.resumesCard = resumesCard;
    }

    public List<ResumesCard> getResumesCardList() {
        return resumesCardList;
    }

    public void setResumesCardList(List<ResumesCard> resumesCardList) {
        this.resumesCardList = resumesCardList;
    }
}
