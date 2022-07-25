package com.kb.user.dao;

import com.kb.user.pojo.collections.Collections;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-23 - 17:26
 */
@Repository
public interface CollectionsMapper {

    /**
     * 收藏夹列表
     * @param userId
     * @return
     */
    List<Collections> list(Long userId);

    /**
     * 添加收藏夹
     * @param collections
     * @return
     */
    Integer add(Collections collections);

    /**
     * 重命名收藏夹
     * @param collections
     * @return
     */
    Integer update(Collections collections);

    /**
     * 删除收藏夹
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 收藏夹详情
     * @param id
     * @return
     */
    Collections detail(Long id);
}
