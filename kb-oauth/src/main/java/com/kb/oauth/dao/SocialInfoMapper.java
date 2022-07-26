package com.kb.oauth.dao;

import com.kb.oauth.pojo.SocialInfo;
import org.springframework.stereotype.Repository;

/**
 * @author wjx
 * @description 第三方账号信息
 */
@Repository
public interface SocialInfoMapper {
    SocialInfo selectOne(String socialId);

    Integer insertOne(String socialId,Integer userId);
}
