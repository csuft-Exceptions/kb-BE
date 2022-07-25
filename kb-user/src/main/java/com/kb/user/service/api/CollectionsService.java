package com.kb.user.service.api;

import com.kb.common.base.BaseResponse;
import com.kb.user.pojo.collections.Collections;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-23 - 16:47
 */
public interface CollectionsService {
    /**
     * 展示用户的收藏夹
     * @param userId
     * @return
     */
    BaseResponse list(Long userId);

    /**
     * 添加收藏夹
     * @param collections
     * @return
     */
    BaseResponse add(Collections collections);

    /**
     * 重命名
     * @param collections
     * @return
     */
    BaseResponse update(Collections collections);

    /**
     * 删除收藏夹
     * @param id
     * @return
     */
    BaseResponse delete(Long id);

    /**
     * 详情接口
     * @param id
     * @return
     */
    BaseResponse detail(Long id);
}
