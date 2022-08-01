package com.kb.file.pojo.module;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-29 - 16:16
 */
public class Module {
    private Long id;

    private String moduleName;

    private Integer grade;

    private String optValue;

    private Integer deleteState;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;


}