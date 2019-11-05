package com.fxd.entity;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public class ResumesCard {

    private Long resumesCardId;
    private PersonInfo personInfo;
    private ResumesInfo resumesInfo;
    private String resumesCardDesc;
    private ResumesLabel resumesLabel;

    public ResumesLabel getResumesLabel() {
        return resumesLabel;
    }

    public void setResumesLabel(ResumesLabel resumesLabel) {
        this.resumesLabel = resumesLabel;
    }

    public Long getResumesCardId() {
        return resumesCardId;
    }

    public void setResumesCardId(Long resumesCardId) {
        this.resumesCardId = resumesCardId;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public ResumesInfo getResumesInfo() {
        return resumesInfo;
    }

    public void setResumesInfo(ResumesInfo resumesInfo) {
        this.resumesInfo = resumesInfo;
    }

    public String getResumesCardDesc() {
        return resumesCardDesc;
    }

    public void setResumesCardDesc(String resumesCardDesc) {
        this.resumesCardDesc = resumesCardDesc;
    }
}
