package com.kb.job.pojo.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 17:28
 */
public class Video implements Serializable {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String videoName;

    @Field(type = FieldType.Text)
    private Integer category;

    @Field(type = FieldType.Date)
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
