package com.fxd.enums;

/**
 * 相关 状态值
 */
public enum StateEnum {
    CHECK(0, "CHECK"),
    OFFLINE(-1, "OFFLINE"),
    SUCCESS(1,"SUCCESS"),
    PASS(2,"PASS"),
    INNER_ERROR(-1001,"INNER_ERROR"),
    NULL_ID(-1002,"NULL_ID"),
    NULL_INFO(-1003,"NULL_INFO"),
    EMPTY_LIST(-1004, "添加数量少于1");

    private int state;
    private String stateInfo;

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

    StateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    // TODO::依据转入的state返回相应的enum
    public static StateEnum stateOf(int state){
        for (StateEnum stateEnum : values()){
            if (stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }
}
