package com.fxd.entity;

import java.util.Date;

public class ResumesLabel {

    private Long resumesLabelId;
    private String resumesLabelName;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //封装自身对象
    private ResumesCard resumesCard;
    private Long resumesCardId;

    public Long getResumesCardId() {
        return resumesCardId;
    }

    public void setResumesCardId(Long resumesCardId) {
        this.resumesCardId = resumesCardId;
    }

    public Long getResumesLabelId() {
        return resumesLabelId;
    }

    public void setResumesLabelId(Long resumesLabelId) {
        this.resumesLabelId = resumesLabelId;
    }

    public String getResumesLabelName() {
        return resumesLabelName;
    }

    public void setResumesLabelName(String resumesLabelName) {
        this.resumesLabelName = resumesLabelName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public ResumesCard getResumesCard() {
        return resumesCard;
    }

    public void setResumesCard(ResumesCard resumesCard) {
        this.resumesCard = resumesCard;
    }
}
