package com.kb.job.pojo.comment;

import com.kb.common.base.BaseQuery;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 15:40
 */
public class SecondCommentParam extends BaseQuery {

    private Long videoId;

    private Long parentId;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
