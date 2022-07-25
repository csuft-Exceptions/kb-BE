package com.kb.user.pojo.userCollection;

import com.kb.common.base.BaseQuery;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-23 - 17:18
 */
public class UserCollectionParam extends BaseQuery {
    private Long userId;

    private Long collectionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

}
