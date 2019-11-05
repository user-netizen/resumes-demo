package com.fxd.entity;

import java.util.Date;

public class ResumesInfo {

    private Long resumesInfoId;
    private PersonInfo personInfo;
    private String resumesInfoName;
    private String resumesInfoGender;

    private Integer resumesInfoAge ;
    private String bornYear;
    private String bornCity;
    private String resumesInfoEducation;
    private String graduationCity;
    private String contactWay;
    private String contactAddr;
    //-1：无经验，0：实习，1，2，3
    private Integer workTimeLimit;
    private String workExperience;
    private String selfIntroduction;
    private String resumesUserImg;
    private Integer pageView;
    private Integer editNumber;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //-1.不可用 0.审核中 1.可用
    private Integer enableStatus;
    //超级管理员给店家的提醒
    private String advice;

    public Long getResumesInfoId() {
        return resumesInfoId;
    }

    public void setResumesInfoId(Long resumesInfoId) {
        this.resumesInfoId = resumesInfoId;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public String getResumesInfoName() {
        return resumesInfoName;
    }

    public void setResumesInfoName(String resumesInfoName) {
        this.resumesInfoName = resumesInfoName;
    }

    public String getResumesInfoGender() {
        return resumesInfoGender;
    }

    public void setResumesInfoGender(String resumesInfoGender) {
        this.resumesInfoGender = resumesInfoGender;
    }

    public Integer getResumesInfoAge() {
        return resumesInfoAge;
    }

    public void setResumesInfoAge(Integer resumesInfoAge) {
        this.resumesInfoAge = resumesInfoAge;
    }

    public String getBornYear() {
        return bornYear;
    }

    public void setBornYear(String bornYear) {
        this.bornYear = bornYear;
    }

    public String getBornCity() {
        return bornCity;
    }

    public void setBornCity(String bornCity) {
        this.bornCity = bornCity;
    }

    public String getResumesInfoEducation() {
        return resumesInfoEducation;
    }

    public void setResumesInfoEducation(String resumesInfoEducation) {
        this.resumesInfoEducation = resumesInfoEducation;
    }

    public String getGraduationCity() {
        return graduationCity;
    }

    public void setGraduationCity(String graduationCity) {
        this.graduationCity = graduationCity;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    public Integer getWorkTimeLimit() {
        return workTimeLimit;
    }

    public void setWorkTimeLimit(Integer workTimeLimit) {
        this.workTimeLimit = workTimeLimit;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getResumesUserImg() {
        return resumesUserImg;
    }

    public void setResumesUserImg(String resumesUserImg) {
        this.resumesUserImg = resumesUserImg;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Integer getEditNumber() {
        return editNumber;
    }

    public void setEditNumber(Integer editNumber) {
        this.editNumber = editNumber;
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

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
