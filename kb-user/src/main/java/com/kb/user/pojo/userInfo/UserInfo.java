package com.kb.user.pojo.userInfo;

import java.util.Date;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-23 - 23:48
 */
public class UserInfo {

    private Long id;

    /**
     * 用户编号,唯一
     */
    private String userNo;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 头像url
     */
    private String face;

    /**
     * 生日
     */
    private String birthday;
    /**
     * 签名
     */
    private String sign;

    /**
     * 性别 男/女/保密 ,0/1/2
     */
    private Integer sex;

    /**
     * 等级level0-6
     */
    private Integer rank;

    private Long coins;

    /**
     * 粉丝数
     */
    private Long followers;

    /**
     * 关注数
     */
    private Long followering;

    /**
     * 动态数
     */
    private Integer dynamicCount;

    /**
     * 是否被禁言,0-未被禁言,1-被禁言
     */
    private Integer silence;

    /**
     * 加入时间
     */
    private Date joinTime;

    private String email;

    private String tel;

    private Date updateTime;

    /**
     * 是否删除,0-未删除,1-已删除
     */
    private Integer deleteState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }

    public Long getFollowering() {
        return followering;
    }

    public void setFollowering(Long followering) {
        this.followering = followering;
    }

    public Integer getDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(Integer dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public Integer getSilence() {
        return silence;
    }

    public void setSilence(Integer silence) {
        this.silence = silence;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }
}
