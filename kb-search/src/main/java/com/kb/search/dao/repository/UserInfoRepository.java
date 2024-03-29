package com.kb.search.dao.repository;

import com.kb.search.pojo.search.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-27 - 22:14
 */
public interface UserInfoRepository extends ElasticsearchRepository<UserInfo,Long> {

}
