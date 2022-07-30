package com.kb.search.pojo.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 17:28
 */
 @Document(indexName = "videos")
public class Video implements Serializable {

    @Id
    private Long id;
    @Field(type = FieldType.Long)
    private Long userId;

     @Field(type = FieldType.Text)
    private String name;

    private String url;

    private Integer category;

    private Date expirationTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date)
    private Date updateTime;

    private Integer deleteState;

    private Long likes;

    private Long plays;

    private Long barrages;

    private Long barrageId;

    private String picUrl;
    @Field(type = FieldType.Text)
    private String Introduction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getPlays() {
        return plays;
    }

    public void setPlays(Long plays) {
        this.plays = plays;
    }

    public Long getBarrages() {
        return barrages;
    }

    public void setBarrages(Long barrages) {
        this.barrages = barrages;
    }

    public Long getBarrageId() {
        return barrageId;
    }

    public void setBarrageId(Long barrageId) {
        this.barrageId = barrageId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }
}
