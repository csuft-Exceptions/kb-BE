package com.kb.user.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.userCollection.UserCollection;
import com.kb.user.pojo.userCollection.UserCollectionParam;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-23 - 17:16
 */
public interface UserCollectionService {
    /**
     * 收藏
     * @param userCollection
     * @return
     */
    BaseResponse add(UserCollection userCollection);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    BaseResponse delete(Long id);

    /**
     * 查看收藏夹中的内容
     * @param userCollectionParam
     * @return
     */
    BaseResponse list(UserCollectionParam userCollectionParam);
}
